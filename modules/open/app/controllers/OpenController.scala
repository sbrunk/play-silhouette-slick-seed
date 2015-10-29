package controllers.open

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import javax.inject.Inject
import models.User
import play.api.i18n.MessagesApi
import scala.concurrent.Future

/*
import play.api._
import play.api.mvc._
import play.api.routing._
import play.api.ApplicationLoader.Context
import router.Routes
class MyComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val router = new Routes(httpErrorHandler, applicationController, barRoutes, assets)

  lazy val barRoutes = new bar.Routes(httpErrorHandler)
  lazy val applicationController = new controllers.Application()
  lazy val assets = new controllers.Assets(httpErrorHandler)
}
*/

/** Controls actions that do not require authenticated users.
  * @param messagesApi Localized messages for this webapp.
  * @param env The Silhouette environment.
  * @param socialProviderRegistry The social provider registry. */
class OpenController @Inject() (
  val messagesApi: MessagesApi,
  val env: Environment[User, CookieAuthenticator],
  socialProviderRegistry: SocialProviderRegistry)
  extends Silhouette[User, CookieAuthenticator] {

  /** Handles unauthenticated user actions. */
  def index = UserAwareAction.async { implicit request =>
    Future.successful(Ok(views.html.openHome(request.identity)))
  }
}
