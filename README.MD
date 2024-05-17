# LottieViewer

LottieViewer is an open-source application built with Compose Multiplatform, specifically targeting desktop environments. The application allows users to drag and drop Lottie files (.json) to play and preview animations. It leverages the ViewModel to handle the application state and utilizes the awesome [Compottie](https://github.com/alexzhirkevich/compottie) library for rendering Lottie animations on the desktop.

## Features

- **Drag and Drop**: Easily drag and drop Lottie files to play them.
- **ViewModel Integration**: Utilizes the latest multiplatform ViewModel to manage the application state.

## Overview

https://github.com/manuel-martos/LottieViewer/assets/4267427/a91c40a8-6123-450b-b686-8a2c00eb5aa2

## Getting Started

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/manuel-martos/LottieViewer
    ```
2. Open Android Studio.
3. In Android Studio, select **File > Open...**.
4. Navigate to the cloned repository directory and select the `build.gradle.kts` file or the project root directory, then click **OK** to open the project.
5. Let Android Studio sync the project. This may take a few minutes as it downloads necessary dependencies.

### Running the Application

1. In the **Gradle** tool window (usually on the right side of Android Studio), expand the `composeApp` project.
2. Within the `composeApp` project, expand **Tasks > compose desktop**.
3. Double-click the `run` task to launch the application.

Once the application is running, you can drag and drop any Lottie file (.json) into the application window, and the animation will automatically start playing.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Compose Multiplatform](https://github.com/JetBrains/compose-jb)
- [Compottie](https://github.com/alexzhirkevich/compottie)

## Contact

For any inquiries, please contact [mmartos@degirona.info](mailto:mmartos@degirona.info).
