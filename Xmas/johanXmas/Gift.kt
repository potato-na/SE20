import kotlin.random.Random

class Gift {
    private var message = "";
    private val gifts = listOf(
            "the new Iphone, lucky !",
            "a banana, healthy Xmas !",
            "beautiful clothes, go try them on !",
            "some socks, it socks...",
            "a road trip to Las Vegas, let's gamble !",
            "money money money");

    fun getMessage(): String {
        return message;
    }

    fun whatKindOfGift(){
        val rng = Random.nextInt(1,6);
        message = "You have been offered " + gifts[rng];
    }
}