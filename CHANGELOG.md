<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# xdebug-skip Changelog

## 0.3.0 - 2023-09-13
### Added
- Option to halt on breakpoints in skipped files
- Only skip includes if the file has a php extension
- Added option to skip includes if they have different extensions
- Added option to skip constructors.
### Bugfixes
- Fixed wrong assignment of values in reset settings
- Fixed [#8 Skip includes also skips template files](https://github.com/tschallacka/phpstorm-xdebug-skip/issues/8)
- Merged [#7 "Skip includes" checkbox using correct settings value](https://github.com/tschallacka/phpstorm-xdebug-skip/pull/7)
## 0.2.0 - 2023-08-19
### Removed
- Removed namespace matching as adding namespaces was not working properly
### Changed
- Renamed plugin to remove phpstorm moniker
## 0.1.0 - 2023-08-17
### Added
- Initial release
- Use stepinto to exit whereever the breakpoint resolves to a non match.
- Added settings panel to configure the plugin
- Namespace and filepath selector for paths to skip when debugging
- Added extra text as the namespace selector doesn't appear on the first click on +
## [Unreleased]
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
