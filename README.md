# LaserDiodeDriverUI
This is an [EMU](https://micro-manager.org/wiki/EMU) plugin for [Micro-Manager](https://github.com/micro-manager/micro-manager) to provide a graphical user interface for the [LaserDiodeDriver](https://github.com/john-wigg/LaserDiodeDriver) device adapter. This UI-plugin was designed by John Wigg and is part of the LaserEngine project by Daniel Schröder at the FSU Jena.

## Installation

1. Please install the [LaserDiodeDriver](https://github.com/john-wigg/LaserDiodeDriver) device adapter first.

1. If EMU is not installed yet (in Micro-Manager, check *Plugins* -> *User Interface*), please install [EMU](https://micro-manager.org/wiki/EMU) first according to the link.

1. Copy the provided .jar file of the [latest release](https://github.com/john-wigg/LaserDiodeDriverUI/releases/tag/v0.3) to *ImageJ* -> *EMU* (On Microsoft Windows it is *Micro-Manager* -> *EMU*). If the EMU folder doesn't exist yet, please create one.

1. Configure your desired UI or copy a pre-configured example available from [here](https://github.com/john-wigg/LaserDiodeDriverUI/tree/master/default%20config) into the EMU folder.

#### Dark grey design example

![](../master/LaserDriverUI_8.2021.png?raw=true)

#### Light grey design example

![](../master/screen.png?raw=true)

#### Build instructions

In alternative of installing the provided .jar files, you can also build this interface from source. Please refer to the [EMU Tutorial](https://jdeschamps.github.io/EMU-guide/tutorial/) for instructions.
