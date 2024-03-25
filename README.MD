# MountPacker
Актуальная версия: 1.1.0

Данная программа позволяет перепаковывать программы Java с уменьшением занимаемого места.
Имеет в себе уже встроенный графический интерфейс базирующийся на библиотеке JavaFX.

### Как это работает?
Для уменьшения места используются 2 ключевых подхода:
* Установка уровня компресии архива.
* Трасформация байткода для удаления отладочной информации, информации об исходных файлах и т.д.

Совместно с двумя упомянутыми выше подходами используется трансформация для увеличения производительности.

### Требования для работы программы
* Java 21 и выше
* JavaFX 21 и выше

### Информация об трансформации

Трансформации удаления ненужной информации (Shrinking):
* DeprecatedAccessRemoveTransformer
* InnerClassRemoveTransformer
* LineNumberRemoveTransformer
* LocalVariableRemoveTransformer
* OuterRemoveTransformer
* SignatureRemoveTransformer
* SourceDebugRemoveTransformer
* SourceFileRemoveTransformer
* SyntheticAccessRemoveTransformer

Трансформации оптимизации (Optimization):
* GotoGotoInlineTransformer
* GotoReturnInlineTransformer
* NopRemoveTransformer