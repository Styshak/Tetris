Tetris
======
Классическая игра под названием "Тетрис".

При запуске игры(jar-файл). Программа проверяет существует ли уже база данных:
 
 1) Если существует стартует программа окном для авторизации.
 
 2) Если не существует создается база данных и в нее записывается дефолтный аккаунт администратора.

Начальное окно после запуска - это окно авторизации, так же присутствует "ссылка" на регистрацию и кнопка подтверждения "ОК".
Авторизации представляет собой два поля - логин и пароль. Регистрация - создается новый фрейм с тремя полями логин, пароль и подтверждения пароля.

 Авторизация происходит следующим образом:
 1) Считываются поля логи и пароль.
 2) Проверяется не пустые ли поля, если пустые выводится окно с сообщением.
 3) Берется считаный логин пользователя и сравнивается со всеми юзерами(логины), которые содержаться в БД, если нет  совпадение выводиться сообщение.
 4) Если логин совпал, берется у юзера с БД под этим логином пароль и сравнивается с считаным с поля "пароль", если не совпадает, выводится сообщение.


В программе различается две категории пользователей:
- админ
- пользователи 

После входа под администратором имеется таблица с информацией о всех юзерах в БД с такими полями (№, логин, пароль, скорость и очки)
Справа от таблицы имеется 4 поля для добавление нового юзера(все кроме №, автоматически инкрементирует).
Внизу фрейма 3 кнопки: удалить, изменить, добавить

При входе под юзером появляется фрейм с главным меню:новая игра, опции, рекорды, выход.

При нажатии на "новая игра" игра тетрис стартует, в стутус баре снизу отображается состояние игры(пауза), если нажата пауза, если не нажата, то кол-во удаленных линий.
Управление игрой:
- кнопки "вверх" и "вниз" поварачивают фигуры
- кнопки "вправо" и "влево" перемещают фигуру по полю
- кнопка "d" ускоряет падание фигуры
- нопка "пробел" сразу опускает фигуру вниз фрейма
- кнопка "p" ставим на паузу(возобновляет игру, если она уже нажата)

Меню "опции" представляет собой фрейм в котором можно в комбобоксе выбрать скорость игры (медленная, средняя, быстрая). По дефолту стоит среднняя.
Меню "рекорды" появляется фрейм с таблицой всех рекордов юзеров (№, логин, скорость и очки).
Меню "выход" - выход из главного меню в окно авторизации.
