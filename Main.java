public class Main {

    public static void main(String[] args) {

        // (здесь в main пользовватель обращается к моему созданнойму классу) 
        // делаю изменяемый объект со значеньем 627
        // MutableData как сумка, содержащая число
        // сумку можно открыть и заменить число
        MutableData originalData = new MutableData(627);

        // делаю теперь неизменяемый контейнер
        // Внутри ImmutableContainer будет храниться клон originalData а не его ориг.

        ImmutableContainer container =
                new ImmutableContainer(originalData);


        // меняю исходную сумку
        originalData.setValue(150);

        // Проверяю что внутри container
        // Несмотря на то что originalData мы изменили на 150 внутри container осталось старое число 627(потому-что container хранит отдельную копию)
        System.out.println("После изменения originalData:" + container.getData().getValue());

        // получаюданные из container, но getData() тоже должна вернуть копию, и изза этого мы не получаем прямой доступ к внутреннему объекту
        MutableData copiedData = container.getData();

        // Изменяем полученную копию
        copiedData.setValue(777);

        // опять проверка container
        // Внутри по-прежнему 627
        // То есть изменение copiedData не смогло изменить
        // внутренний объект нашего ImmutableContainer
        System.out.println("После изменения copiedData: "   + container.getData().getValue());
    }
}


/* 
  Изменяемый класс (mutable) (коробка с числом внутри)
  Число можно заменить с помощью сеттера setValue()
 */
class MutableData {

    // В этой переменной хранится наше число (private поля чтобы сохранить инкапсуляцию)
    private int value;


    // объект
    // Он используется, когда мы создаём новую коробку
    // new MutableData(627)
    // В результате внутри коробки будет value = 627.
    public MutableData(int value) {
        this.value = value;
    }

    // объект копирования (создаёт свежий объект с таким же значением)
    // MutableData first = new MutableData(627)
    // MutableData second = new MutableData(first)
    // first и second будут два разных объекта но в обоих будет число 627

    public MutableData(MutableData other) {
        this.value = other.value;
    }

    // объект позволяет изменить число внутри коробки
    // Например data.setValue(150);
    // data.setValue(150);
    // после этого внутри объекта будет 150
    public void setValue(int value) {
        this.value = value;
    }


    // Метод позволяет узнать число внутри коробки
    public int getValue() {
        return value;
    }
}


/*
 * неизменяемый класс должен хранить MutableData так чтобы внешний код не мог изменить внутреннее состояние
 */
final class ImmutableContainer {

    /*
     * final - переменная data после создания контейнера не сможет начать ссылаться на другой объект
     * final не делает MutableData неизменяемым ( нужны дополнительные копии)
     */
    private final MutableData data; // инкапсуляция


    /*
     * объект ImmutableContainer получили некий MutableData
     * нет this.data = data  делаю копию
     */
    public ImmutableContainer(MutableData data) {

        // делаю отдельный объект с таким же значением
        // Было originalData -> 627
        // После этой строки: originalData -> 627, container.data -> 627
        // Это две разные ячейки (коробки)
        this.data = new MutableData(data);
    }

    /*
     * Метод возвращает данные из контейнера
     *
     * для геттера не делаем return ведь тогда внешний код получит прямой доступ к внут.объекту
     * Вместо этого снова копия
     */
    public MutableData getData() {

        // Отдаём копию вместо оригинального внутр. объекта
        return new MutableData(this.data);
    }
}
