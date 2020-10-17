# Описание
Предназначен для создания sql скрипта для записывания данных в БД.

**Примечание**: 
Изначально безовая сущность xml выглядит так:
```
<company>
        <country lang="ru">Россия</country>
        <company-id>gazprombank_445774</company-id>
        <address lang="ru">Астраханская область, Астрахань, ул. Адмирала Нахимова 60В, ГК "Золотой Затон"</address>
        <url>https://www.open.ru</url>
        <coordinates>
            <lat>46.30717</lat>
            <lon>47.98729</lon>
        </coordinates>
        <actualization-date>21.07.2020</actualization-date>
        <feature-boolean name="public" value="1"/>
        <feature-boolean name="cash_in" value="0"/>
        <name lang="ru">Банкомат Газпромбанк</name>
        <working-time>Круглосуточно</working-time>
        <rubric-id>184105402</rubric-id>
</company>
```
После скачивания xml из open.ru все сущности выглядят как указано выше

Скрипт меняет формат данных следующим образом:
```
 lang="ru" = ""
<feature-boolean name="public" value="1"/> = <feature-boolean_public>1</feature-boolean_public>
<feature-boolean name="public" value="0"/> = <feature-boolean_public>0</feature-boolean_public>
<feature-boolean name="cash_in" value="1"/> = <feature-boolean_cash>1</feature-boolean_cash>
<feature-boolean name="cash_in" value="0"/> = <feature-boolean_cash>0</feature-boolean_cash>
```
далее пишет каждую сущность в виде запроса insert в выходной файл

для запуска необходимо скачать репозиторий, зайти в него из командной строки и ввести
```
sbt package
```
перейти в папку libs
```
cd target/scala-2.13
```
запустить jar-ник
```
java -jar dbsc-assembly-0.1.jar [вместо этих скобок имя cкачанного xml] [вместо этих имя выходного файла типа output.sql]
```
получившийся sql наполнит базу данными