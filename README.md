# Detection Aksara Batak Toba app using YOLOv8 and Android

## Step 1 (Train and export Object detection model):

## Step 2 (Object detection android app setup):
- Open android_app folder.

- Put your .tflite model and .txt label file inside the assets folder. You can find assets folder at this location: <b> android_app\android_app\app\src\main\assets</b>

- Rename paths of your model and labels file in Constants.kt file. You can find Constants.kt at this location: <b>android_app\android_app\app\src\main\java\com\aksarabatak\yolov8tflite </b>

- Download and install Android Studio from the official website (https://developer.android.com/studio)

- Once installed, open Android Studio from your applications menu.

- When Android Studio opens, you'll see a welcome screen. Here, you'll find options to create a new project, open an existing project, or check out project from version control.Since you already have a project, click on "Open an existing Android Studio project".

- Navigate to the directory where your project is located and select the project's root folder. 

- Build and Run
