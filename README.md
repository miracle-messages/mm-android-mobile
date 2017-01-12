Miracle Messages for Android 
============================

This is the official mobile application built for the non-profit organization, Miracle Messages.

Read all about the organization [here](http://miraclemessages.org/).

The application hopes to provide an efficient way for volunteers using Android phones to record and deliver short video messages from our homeless neighbors to their loved ones. There is also an iOS-variant of the application available. Both variants use Amazon AWS and Google Firebase for storage and analytics.

Typical User Workflow
---------------------
1. User signs in using his/her Full Name, Email Address, Phone Number, and Location
2. User is greeted with a menu with the following options:
  * My Profile (go to 2a)
  * About (go to 2b)
  * Resources (go to 2c)
  * FAQ (go to 2d)
  * Record a message (go to 2e)
  * Contact (go to 2f)

2a. User may update the credentials as entered in (1).

2b. User can view the About page, with a description of the organization. There is also the ability to view the Privacy Policy and leave feedback on the Play Store.

2c. User sees hyperlinks to links where they may connect with fellow volunteers on social media, as well as important documents on the organization.

2d. User is redirected to the Miracle Messages website's FAQ page on the internet browser.

2e. User sees a script of what to say to the homeless individual (i.e. Permission to record, take information, etc.). Then the user is greeted with a form. The form consists of two parts: **From** and **To**. The **From** part of the form is information about the sender of the Miracle Message, the homeless individual. The subsequent **To** part of the form that follows after is the information about the recipient of the Miracle Message, the loved one to be reached. 

After the forms are completed as best of the homeless individual's ability, the user grants permission to the camera, audio, and internal/external storage. The phone's default camera opens and the user can start recording when ready. After the user is done, the user sees the Upload page. Clicking on the Submit button uploads the video to an Amazon S3 bucket, while the form credentials and video URL are sent to Google Firebase for storage. 

The user then continues to the What's Next page, where they may get in touch with their local chapters, track the progress of their submitted miracle message(s) (this feature has not been completed yet, so only a Toast notification is provided), or go home back to (2).

Technical Specifications
------------------------
* Android Studio v2.2.3
* Google-services v3.0.0
* MinSdkVersion 19 aka Android 4.4, KitKat
* TargetSdkVersion 24 aka Android 7.0 Nougat
* Java SDK v1.7
* Amazon AWS SDK v2.2.+
* Firebase Database v9.8.0
* Google Volley v1.0.0

Requested User Permissions
--------------------------
* Camera
* Record Audio
* Write External Storage
* Read External Storage
* Internet
* Get Accounts
* Access Network State
