package controllers.admin

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Environment, Silhouette }
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import models.User
import play.api.i18n.MessagesApi

import scala.concurrent.Future

/** Controls administrative user actions.
  * @param messagesApi The Play messages API.
  * @param env The Silhouette environment.
  * @param socialProviderRegistry The social provider registry. */
class AdminController @Inject() (
  val messagesApi: MessagesApi,
  val env: Environment[User, CookieAuthenticator],
  socialProviderRegistry: SocialProviderRegistry)
  extends Silhouette[User, CookieAuthenticator] {

  /** Handle administrative user functions. */
  def index = SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.adminHome(request.identity)))
  }
}
