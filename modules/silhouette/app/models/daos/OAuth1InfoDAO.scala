package models.daos

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.daos.DelegableAuthInfoDAO
import com.mohiva.play.silhouette.impl.providers.OAuth1Info
import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

/**
 * The DAO to store the OAuth1 information.
 */
class OAuth1InfoDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends DelegableAuthInfoDAO[OAuth1Info] with DAOSlick {

  import driver.api._

  protected def oAuth1InfoQuery(loginInfo: LoginInfo) = for {
    dbLoginInfo <- loginInfoQuery(loginInfo)
    dbOAuth1Info <- slickOAuth1Infos if dbOAuth1Info.loginInfoId === dbLoginInfo.id
  } yield dbOAuth1Info

  // Use subquery workaround instead of join to get authinfo because slick only supports selecting
  // from a single table for update/delete queries (https://github.com/slick/slick/issues/684).
  protected def oAuth1InfoSubQuery(loginInfo: LoginInfo) =
    slickOAuth1Infos.filter(_.loginInfoId in loginInfoQuery(loginInfo).map(_.id))

  protected def addAction(loginInfo: LoginInfo, authInfo: OAuth1Info) =
    loginInfoQuery(loginInfo).result.head.flatMap { dbLoginInfo =>
      slickOAuth1Infos += DBOAuth1Info(None, authInfo.token, authInfo.secret, dbLoginInfo.id.get)
    }.transactionally

  protected def updateAction(loginInfo: LoginInfo, authInfo: OAuth1Info) =
    oAuth1InfoSubQuery(loginInfo).
      map(dbOAuthInfo => (dbOAuthInfo.token, dbOAuthInfo.secret)).
      update((authInfo.token, authInfo.secret))

  /**
   * Finds the auth info which is linked with the specified login info.
   *
   * @param loginInfo The linked login info.
   * @return The retrieved auth info or None if no auth info could be retrieved for the given login info.
   */
  def find(loginInfo: LoginInfo): Future[Option[OAuth1Info]] = {
    val result = db.run(oAuth1InfoQuery(loginInfo).result.headOption)
    result.map { dbOAuth1InfoOption =>
      dbOAuth1InfoOption.map(dbOAuth1Info => OAuth1Info(dbOAuth1Info.token, dbOAuth1Info.secret))
    }
  }

  /**
   * Adds new auth info for the given login info.
   *
   * @param loginInfo The login info for which the auth info should be added.
   * @param authInfo The auth info to add.
   * @return The added auth info.
   */
  def add(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] =
    db.run(addAction(loginInfo, authInfo)).map(_ => authInfo)

  /**
   * Updates the auth info for the given login info.
   *
   * @param loginInfo The login info for which the auth info should be updated.
   * @param authInfo The auth info to update.
   * @return The updated auth info.
   */
  def update(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] =
    db.run(updateAction(loginInfo, authInfo)).map(_ => authInfo)

  /**
   * Saves the auth info for the given login info.
   *
   * This method either adds the auth info if it doesn't exists or it updates the auth info
   * if it already exists.
   *
   * @param loginInfo The login info for which the auth info should be saved.
   * @param authInfo The auth info to save.
   * @return The saved auth info.
   */
  def save(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] = {
    val query = loginInfoQuery(loginInfo).joinLeft(slickOAuth1Infos).on(_.id === _.loginInfoId)
    val action = query.result.head.flatMap {
      case (dbLoginInfo, Some(dbOAuth1Info)) => updateAction(loginInfo, authInfo)
      case (dbLoginInfo, None)               => addAction(loginInfo, authInfo)
    }.transactionally
    db.run(action).map(_ => authInfo)
  }

  /**
   * Removes the auth info for the given login info.
   *
   * @param loginInfo The login info for which the auth info should be removed.
   * @return A future to wait for the process to be completed.
   */
  def remove(loginInfo: LoginInfo): Future[Unit] =
    db.run(oAuth1InfoSubQuery(loginInfo).delete).map(_ => ())
}
