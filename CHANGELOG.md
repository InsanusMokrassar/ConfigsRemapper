# Changelog of ConfigsRemapper

## v0.2.1b

* Renamed `TelegramBotHelperConfigModel` to `ConfigModel`

## v0.2.2b

* Added `handlingMixinObject` to [ReceiversManager](src/main/kotlin/com/github/insanusmokrassar/ConfigsRemapper/ReceiversManager.kt)
* Fixed [ConfigModel#makeParamsObject argument](src/main/kotlin/com/github/insanusmokrassar/ConfigsRemapper/ConfigModel.kt#33) (`IObject<Any>` -> `CommonIObject<String, Any>`)

## v0.2.3b

* Hotfix of constructors of ReceiversManager

## v0.2.4b

* Added possibility to use `handle` in ReceiversManager as async or sync receiver.

## v0.2.5b

* Replace config argument type in `ReceiversManager#handle` by `CommonIObject<String, Any`
* Upgraded method of catching exceptions in `ReceiversManager#handle` 
