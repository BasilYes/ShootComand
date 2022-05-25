# Описание проекта Shoot Command
## Об игре
Shoot Command это мобильная игра в жанре __shoot 'em up__ с элементами __bullet hell__. В ней игроку предстоит управлять космическим кораблем, что бороздит бесконечный космос, отстреливая по пути нескончаемые орды разных врагов. Отличтельной особенностью игры является возможность управления кораблем с помощью гироскопа в телефоне. Проект был разработан на Android Studio
## Скриншоты
![](https://sun9-31.userapi.com/s/v1/ig2/fiu5L7sbgxYNQAAMXaGRl2ZwHV570tGZgMl2Hkd8Q3CUteL11Md-wf6aE4mjBs1zHtaOhPdSwpy-TKWoqLqT1sym.jpg?size=738x1600&quality=95&type=album)
![](https://sun9-76.userapi.com/s/v1/ig2/scUVhEPrQiTRl22_IF6yOBOc-oyvo2Tz92GcuAkiqzU-4ChinBU-_yT6VstiasUopiBmhDBNdB8sw_VuyFA6BW4N.jpg?size=738x1600&quality=95&type=album)
## Команда разработчиков
*	[Василий Реуков](https://github.com/BasilYes) – главный разработчик игрового движка и геймдизайнер, ответственный за всю физику игры и внутриигровую логику;
*	[Бугров Егор](https://github.com/ExTimeGameing) – проектный менеджер и геймдизайнер-математик, ответственный за подсчет характеристик и игровой баланс;
*	[Лесневская Полина](https://github.com/polinalsn) – арт-директор и графический дизайнер, ответственный за формирование арт-стиля и отрисовку игровых объектов.
# Система сборки проекта
## Зависимости проекта
Для работы проекта необходимо добавить в __build.Gradle__ следующие зависимости:
```
dependencies {
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.6.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```
## Команда запуска
Для установки __apk__ файла можно использовать __ADB__:
```
adb install C:'Ваш путь до приложения'\Shootemup\app\build\intermediates\apk\debug\app-debug.apk
```
