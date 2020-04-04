package machine

import java.lang.IllegalArgumentException

object Machine {
    var money = 550
        private set
    var water = 400
        private set
    var milk = 540
        private set
    var coffee = 120
        private set
    var cups = 9
        private set
    private var state = MachineStates.ACTION

    fun interact(arg: Any) {
        when (state) {
            MachineStates.ACTION -> {
                when (arg) {
                    "buy" -> state = MachineStates.BUY
                    "fill" -> state = MachineStates.FILL
                    "take" -> {
                        println("I gave you \$$money")
                        money = 0
                    }
                }
            }
            MachineStates.BUY -> {
                if (arg is String && arg != "back" && arg.toInt() in 1..3)
                    sellCoffee(CoffeeType.values()[arg.toInt() - 1])
                state = MachineStates.ACTION
            }
            MachineStates.FILL -> if (arg is IntArray) {
                println("Filling!")
                water += arg[0]
                milk += arg[1]
                coffee += arg[2]
                cups += arg[3]
                state = MachineStates.ACTION
            }
        }
    }

    private fun sellCoffee(type: CoffeeType) {
        when {
            type.water > water -> {
                println("Sorry, not enough water!")
                return
            }
            type.milk > milk -> {
                println("Sorry, not enough milk!")
                return
            }
            type.beans > coffee -> {
                println("Sorry, not enough coffee beans!")
                return
            }
            cups == 0 -> {
                println("Sorry, not enough disposable cups!")
                return
            }
            else -> println("I have enough resources, making you a coffee!")
        }
        money += type.price
        water -= type.water
        milk -= type.milk
        coffee -= type.beans
        cups -= 1
    }
}

enum class MachineStates {
    ACTION, BUY, FILL
}

enum class CoffeeType(val price: Int, val water: Int, val milk: Int, val beans: Int) {
    ESPRESSO(4, 250, 0, 16),
    LATTE(7, 350, 75, 20),
    CAPPUCCINO(6, 200, 100, 12)
}