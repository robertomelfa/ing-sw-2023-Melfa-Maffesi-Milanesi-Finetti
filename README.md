# Prova Finale Ingegneria del Software 2023

## Team

- ###  Roberto Melfa  ([@robertomelfa](https://github.com/robertomelfa)) <br> roberto.melfa@mail.polimi.it
- ###  Matteo Maffesi ([@matteomaffesi](https://github.com/matteomaffesi)) <br> matteo.maffesi@mail.polimi.it
- ###  Simone Milanesi ([@simonemilanesi](https://github.com/simonemilanesi)) <br> simone.milanesi@mail.polimi.it
- ###  Christian Finetti ([@christianfinetti](https://github.com/christianfinetti)) <br> christian.finetti@mail.polimi.it

## Requirements

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| Complete rules | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| Socket | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| RMI | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| GUI | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| CLI | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+)|
| Multiple games | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |
| Persistence | ![#c5f015](https://via.placeholder.com/15/008000/000000?text=+) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

# MyShelfie



## Setup

- In the [deliverables/final/jar](deliverables/final/jar) folder there are two multi-platform jar files, one to set the Server up, and the other one to start the Client.
- The Server can be run with the following command, as default it runs on port 8080:
    ```shell
    > java -jar Server-shaded.jar
    ```

- The Client can be run with the following command:
    ```shell
    > java -jar Client.jar
    ```
    - IMPORTANT: before starting the client is important to create two new folders (MyShlefie\saves) in the same directory of jar files. The game files will be saved in this folder.
    - The Server's IP to connect to can be specified during the execution.
    - The choice whether to use the Grafical User Interface (GUI) or the Command Line Interface (CLI) can be specified during the execution.
    

## Tools

* [astahUML](https://astah.net) - UML Diagram
* [Maven](https://maven.apache.org/) - Dependency Management
* [IntelliJ](https://www.jetbrains.com/idea/) - IDE
* [JavaFX](https://openjfx.io) - Graphical Framework

## License

This project is developed in collaboration with [Politecnico di Milano](https://www.polimi.it) and [Cranio Creations](http://www.craniocreations.it).
