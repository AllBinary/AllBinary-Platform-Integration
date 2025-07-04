                             AllBinary Platform Integration

What is it?

This repository contains external dependencies commonly used by the AllBinary Platform.  
This repository is not required for building the AllBinary Platform, but without these 
external dependencies it does not very useful for game/app development.

What is the goal?

The goal of the "AllBinary Platform Integration" repository is to provide common 
dependencies not available with the default maven nexus.  These common dependencies 
will allow you to build the example game: 

https://github.com/AllBinary/AllBinary-Platform-Game-Parent 
https://github.com/AllBinary/AllBinary-Platform-Game

What is the License?

Please see the LICENSE file in the root of the repository.

Where is the other code?

It depends on the https://github.com/AllBinary/AllBinary-Platform.

Who is responsible for all this shit?

Travis Berthelot travisberthelot@hotmail.com

Instructions

After building the AllBinary Platform you can build this project with maven.

1.  Download, Install and Configure Git

    a.  Download: https://git-scm.com/downloads

    b.  Install by extracting the Maven binary archive to the desired location.

    c.  Add the Maven path with the bin folder the the PATH environment variable.

2.  Use git to clone: https://github.com/AllBinary/AllBinary-Platform-Integration

    a.  Enter project directory of your choice.
    b.  git clone https://github.com/AllBinary/AllBinary-Platform-Integration

3.  Enter the root directory of the cloned project.

4.  At the prompt type: mvn clean install
