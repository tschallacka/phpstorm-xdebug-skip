# xdebug-skip

![Build](https://github.com/tschallacka/phpstorm-xdebug-skip/workflows/Build/badge.svg)

[![Version](https://img.shields.io/jetbrains/plugin/v/de.tschallacka.phpstormxdebugskip.svg)](https://plugins.jetbrains.com/plugin/de.tschallacka.phpstormxdebugskip)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/de.tschallacka.phpstormxdebugskip.svg)](https://plugins.jetbrains.com/plugin/de.tschallacka.phpstormxdebugskip)

- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
# Xdebug Skip

Select filepaths in the settings, and you will ignore those when stepping through code with xdebug.
You will resume where the code exits those filepaths.
You can also select to skip constructors, and to halt on breakpoints in skipped files.
This means that if you have a breakpoint in a skipped file, it will halt on that breakpoint, but you cannot step through the file without more breakpoints
This might get added in a future release.

It is also possible to skip includes, so you don't have to watch every file that gets autoloaded by composer or other autoloaders.
By default it will only skip files that have a .php extension, but you can also select to skip files that have a different extension.
This is seperated so you do stop when template files are included, but you can still skip files that are included by composer.

## Usage

1. Open the settings
2. Go to Xdebug Skip
3. Select paths  to ignore
4. Select wether you wish to skip constructors or not
5. select wether you wish to halt on breakpoints in skipped files or not
6. select wether you wish to skip includes/requires of .php files
7. select wether you wish to skip includes if they have different extensions
8. Start debugging
9. Profit

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "phpstorm-xdebug-skip"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/tschallacka/phpstorm-xdebug-skip/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
