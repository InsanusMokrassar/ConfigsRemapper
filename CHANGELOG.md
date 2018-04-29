# Changelog of ConfigsRemapper

## v0.2.1b

* Renamed `TelegramBotHelperConfigModel` to `ConfigModel`

## v0.2.2b

* Added `handlingMixinObject` to [ReceiversManager](src/main/kotlin/com/github/insanusmokrassar/ConfigsRemapper/ReceiversManager.kt)
* Fixed [ConfigModel#makeParamsObject argument](/home/aleksey/projects/ConfigsRemapper/src/main/kotlin/com/github/insanusmokrassar/ConfigsRemapper/ConfigModel.kt#33) (`IObject<Any>` -> `CommonIObject<String, Any>`)
