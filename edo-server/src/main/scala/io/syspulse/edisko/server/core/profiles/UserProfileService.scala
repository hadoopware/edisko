package io.syspulse.edisko.server.core.profiles

import io.syspulse.edisko.server.core.{UserProfile, UserProfileUpdate}
import io.syspulse.edisko.server.utils.MonadTransformers._

import scala.concurrent.{ExecutionContext, Future}

class UserProfileService(
    userProfileStorage: UserProfileStorage
)(implicit executionContext: ExecutionContext) {

  def getProfiles(): Future[Seq[UserProfile]] =
    userProfileStorage.getProfiles()

  def getProfile(id: String): Future[Option[UserProfile]] =
    userProfileStorage.getProfile(id)

  def createProfile(profile: UserProfile): Future[UserProfile] =
    userProfileStorage.saveProfile(profile)

  def updateProfile(id: String, profileUpdate: UserProfileUpdate): Future[Option[UserProfile]] =
    userProfileStorage
      .getProfile(id)
      .mapT(profileUpdate.merge)
      .flatMapTOuter(userProfileStorage.saveProfile)

}
