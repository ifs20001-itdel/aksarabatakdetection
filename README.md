# Detection Aksara Batak Toba app using YOLOv8 and Android

## Step 1 (Train and export Object detection model):
from google.colab import drive
drive.mount('/content/drive')

!unzip '/content/drive/MyDrive/200epoch/MachineLearning.zip'

!pip install ultralytics

!pip install tensorflow==2.16.1

from ultralytics import YOLO

# Load a model
model = YOLO('yolov8n.pt')  # load a pretrained model (recommended for training)

# Train the model with 2 GPUs
results = model.train(data='data.yaml', epochs=200, imgsz=640, device=0)

# Validate model
from ultralytics import YOLO

# Load a model
model = YOLO('/content/runs/detect/train/weights/best.pt')  # load a custom model

# Validate the model
metrics = model.val()  # no arguments needed, dataset and settings remembered
metrics.box.map    # map50-95
metrics.box.map50  # map50
metrics.box.map75  # map75
metrics.box.maps   # a list contains map50-95 of each category

#  Prediction using trained model

from ultralytics import YOLO

# Load a pretrained YOLOv8n model
model = YOLO('runs/detect/train2/weights/best.pt')

# Run inference
model.predict('test_images', save=True, imgsz=640, conf=0.2)

#  Export model to tflite


from ultralytics import YOLO

# Load a model
model = YOLO('runs/detect/train2/weights/best.pt')  # load a custom trained model

# Export the model
model.export(format='tflite')

!zip -r /content/sample_data.zip /content/runs

## Step 2 (Object detection android app setup):
- Open android_app folder.

- Put your .tflite model and .txt label file inside the assets folder. You can find assets folder at this location: <b> android_app\android_app\app\src\main\assets</b>

- Rename paths of your model and labels file in Constants.kt file. You can find Constants.kt at this location: <b>android_app\android_app\app\src\main\java\com\aksarabatak\yolov8tflite </b>

- Download and install Android Studio from the official website (https://developer.android.com/studio)

- Once installed, open Android Studio from your applications menu.

- When Android Studio opens, you'll see a welcome screen. Here, you'll find options to create a new project, open an existing project, or check out project from version control.Since you already have a project, click on "Open an existing Android Studio project".

- Navigate to the directory where your project is located and select the project's root folder. 

- Build and Run
