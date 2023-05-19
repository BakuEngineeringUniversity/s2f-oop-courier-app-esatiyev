import kotlin.random.Random

fun main(args: Array<String>) {
    var recipient = "esatiyev" // bunu siliceksen user sistemmi elave edenden sonra.Userin adini atacaqsan bura
    var sender: String
    var price: Float
    var weight: Float
    var trackingNumber: String

    var packageName: String
    var sizeOfPackageType = 3
    var packages = ArrayList<Package>()
    var couriers = ArrayList<Courier>()
    var courierName: String
    var option: Int
    var condition: Boolean = true
    while(true){
        println("1. Log into app as user")
        println("2. Log into app as admin")
        println("Enter a number: ")
        do {
            option = readln().toInt()
            if (option !in 1 .. 2) print("Enter a valid number: ")
        } while (option !in 1..2)

        when (option) {
            // User Interface
            1 -> {
                println("1. Create New Package")  // user
                println("2. My Packages") // user
                println("3. Profile")
                println("4. Calculator & Tariffs")
                println("0. Sign out")

                when(enterNumber(1, 5)) {
                    // Create a new package
                    1 -> {
                        println("Write your specific package name: ")
                        packageName = readln()
                        println("Choose your package type")
                        println("1: Regular Package")
                        println("2: Express Package")
                        println("3: Fragile Package")
                        var packageType: Int
                        do{
                            packageType = readln().toInt()
                            if (packageType !in (0..sizeOfPackageType)) println("Invalid package type!!! Please enter valid type: ")
                        } while(packageType !in (0..sizeOfPackageType))


                        //   var recipient = "esatiyev" // bunu siliceksen user sistemmi eleave edenden sonra.Userin adini atacaqsan bura
                        println("Write sender name: ")
                        sender = readln()
                        println("Write price of your product: ")
                        price = readln().toFloat()
                        weight = calculateWeight()
                        trackingNumber = setTrackingNumber()

                        when(packageType){
                            // Regular Package
                            1 -> {
                                packages.add(RegularPackage(packageName, trackingNumber, sender, recipient, weight, price))
                                println("Regular Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Express Package
                            2 -> {
                                packages.add(ExpressPackage(packageName, trackingNumber, sender, recipient, weight, price))
                                println("Express Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Fragile Package
                            3 -> {
                                var isFragile: Boolean
                                do{
                                    println("Is package fragile? (Y/n)")
                                    var x: String = readln()
                                    isFragile = x == "Y"
                                } while(x != "Y" && x != "n")
                                packages.add(FragilePackage(packageName, trackingNumber, sender, recipient, weight, price, isFragile))
                                println("Fragile Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            else -> println("Invalid package type!!!")
                        }

                    }
                    // My Packages
                    2 -> {
                        println("1. Add package to specific courier") //user / packages
                        println("2. Remove the package from current courier") // user/ PAckages
                        println("3. Delete the package permanently") // user / packages
                        println("4. Show package information") // user / packages
                        println("0. Back")

                        option = enterNumber(0, 4)
                        if (option == 0) continue

                        var i = 1
                        for(row in packages) {
                            println("$i. Package: ${row.getPackageName()} ")
                            println("    Sender: ${row.getSender()}")
                            println("    Tracking number: ${row.getTrackingNumber()}")
                            //   println("   Price: ${row.getPrice()}")
                            //   println("   Weight: ${col.getWeight()}")
                            //   println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}") }")
                            //   println("   Courier: ${row.getCourierName()}")
                            println()
                            i++
                        }
                        println("0. Back")

                        when(option) {
                            0 -> continue // Back

                            // Add package to specific courier
                            1 -> {
                                option = enterNumber(0, i - 1, "Choose a package")
                                if (option == 0) continue // Back

                                // check for this package is already added to any courier or not
                                var breakExistPackage: Boolean = false

                                if (couriers.isNotEmpty())
                                    secondfor@for (row in couriers) {
                                        if (row.packages.isNotEmpty())
                                            for (col in row.packages) {
                                                if (col.getTrackingNumber() == packages[option - 1].getTrackingNumber()) {
                                                    println("This package is already added to ${row.getCourierName()} cargo.")
                                                    breakExistPackage = true
                                                    break@secondfor
                                                }
                                            }
                                    }
                                if (breakExistPackage) continue

                                i = 1
                                for (row in couriers) {
                                    println("$i. ${row.getCourierName()}, price per kg: ${row.getPricePerKg()}")
                                    i++
                                }
                                println("0. Back\n")

                                val courierNumber: Int = enterNumber(0, i - 1, "Choose a Courier: ")

                                couriers[courierNumber].addPackage(packages[option - 1])
                                println("Package is added to ${couriers[courierNumber].getCourierName()} courier company.")
                            }

                            // Remove the package from current courier
                            2 -> {
                                option = enterNumber(0, i - 1, "Choose a package")
                                if (option == 0) continue

                                println("Do you really want to remove this package from this cargo company?")

                                if (!YorN("Package isn't removed!")) continue

                                // check that this package is already added to any courier or not, if not then "continue"
                                var breakExistPackage: Boolean = true

                                if (couriers.isNotEmpty())
                                    secondfor@for (row in couriers) {
                                        if (row.packages.isEmpty()) continue
                                        for (col in row.packages) {
                                            if (col.getTrackingNumber() == packages[option -1].getTrackingNumber()) {
                                                row.removePackage(col)
                                                println("Package is removed successfully.")
                                                breakExistPackage = false
                                                break@secondfor
                                            }

                                        }
                                    }
                                if (breakExistPackage) {
                                    println("This package isn't added to any courier!")
                                    continue
                                }

                                println("Provide feedback. Why did you remove it from this cargo?")
                                println("1: I saw more cheap courier than this  cargo.")
                                println("2: Delivery time is long.")
                                println("3: I will cancel the order.")

                                println("4: Other.\n")

                                println("Select a option: ")

                                do {
                                    option = readln().toInt()
                                    if (option !in 1..4) println("Please select valid option: ")
                                } while (option !in 1 .. 4)

                                when(option) {
                                    in   1 ..  3 -> println("Thanks for your feedback.")

                                    4 -> {
                                        println("Enter your feedback:\n")
                                        var feedback = readln()
                                        // feedback courier-in datasina gede biler. Elave etmek olar bu ozelliyi de
                                        println("Thanks for your feedback.")
                                    }

                                    else -> println("\nelse\n") // should never happen
                                }
                            }

                            // Delete the package permanently
                            3 -> {
                                option = enterNumber(0, i - 1, "Choose a package")
                                if (option == 0) continue

                                // check that this package is added to any courier or not
                                var isExistPackage: Boolean = false

                                if (couriers.isNotEmpty())
                                    secondfor@for (row in couriers) {
                                        if (row.packages.isNotEmpty())
                                            for (col in row.packages) {
                                                if (col.getTrackingNumber() == packages[option - 1].getTrackingNumber()) {
                                                    println("This package was added to ${row.getCourierName()} cargo" +
                                                            "it cannot be deleted")
                                                    isExistPackage = true
                                                    break@secondfor
                                                }
                                            }
                                    }
                                if (isExistPackage) {
                                    println("For delete this package permanently firstly, you must remove this package from the courier.")
                                    continue
                                }

                                println("Do you really want to delete this package?")
                                if (YorN("Package isn't deleted!")) continue

                                packages.removeAt(option - 1)
                                println("Package is deleted successfully.")
                            }

                            // Show package information
                            4 -> {
                                println("1. Show all packages in couriers:")
                                println("2. Show a package in couriers")
                                println("0. Back")

                                when(enterNumber(0, 2)) {
                                    // Back
                                    0 -> continue

                                    // Show all packages
                                    1 -> {
                                        for(row in couriers) {
                                            for(col in row.packages) {
                                                println("Package : ${col.getPackageName()} ")
                                                println("   Sender: ${col.getSender()}")
                                                println("   Price: ${col.getPrice()}")
                                                println("   Weight: ${col.getWeight()}")
                                                println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}") }")
                                                println("   Courier: ${row.getCourierName()}")
                                                println("   Tracking number: ${col.getTrackingNumber()}")
                                                println()
                                            }
                                            println("\n")
                                        }
                                    }

                                    // Show a package
                                    2 -> {
                                        var i = 1
                                        for (row in couriers) {
                                            for (col in row.packages){
                                                println("$i. ${col.getPackageName()}: #${col.getTrackingNumber()}")
                                                i++
                                            }
                                        }
                                        println("0. Back")

                                        option = enterNumber(0, i - 1)
                                        i = 1
                                        seconder@for (row in couriers) {
                                            for (col in row.packages) {
                                                if (i == option) {
                                                    println("Package : ${col.getPackageName()} ")
                                                    println("   Sender: ${col.getSender()}")
                                                    println("   Price: ${col.getPrice()}")
                                                    println("   Weight: ${col.getWeight()}")
                                                    println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}") }")
                                                    println("   Courier: ${row.getCourierName()}")
                                                    println("   Tracking number: ${col.getTrackingNumber()}")
                                                    println()
                                                    break@seconder
                                                }
                                                i++
                                            }
                                        }
                                    } // end show a package
                                }
                            } // End Show Package
                        }


                    }

                    // Profile
                    3 -> {

                    }

                    // Calculator & Tariffs
                    4 -> {

                    }

                    else -> println("\nelse\n") // never should be
                }
            }

            // Admin Interface
            2 -> {
                println("1. Create a new courier") //admin
                println("2. Delete the courier permanently") // admin
                println("3. Get total revenue of courier(s)") // admin
                println("4. Package Information") //admin
                println("5. Package Operations")

                print("Enter a number: ")
                do {
                    option = readln().toInt()
                    if (option !in 1..5) print("Please enter a valid number: ")
                } while (option !in 1 .. 5)

                when(option) {
                    // Create a new courier at ArrayList of couriers
                    1 -> {
                        var breakSubjectRequest: Boolean = false // bunu labeled breakle ede bilsen sil !!!
                        println("Enter the courier name: ")
                        courierName = readln()

                        // check that this courier with this name has already exist or not
                        for(row: Courier in couriers){
                            if(row.getCourierName() == courierName){
                                println("This courier has already exist!!!")
                                breakSubjectRequest = true
                                //conti@subjectrequest
                            }
                        }
                        if(breakSubjectRequest) continue

                        println("Enter delivery price per kg: ")
                        var pricePerKg: Float
                        do{
                            pricePerKg = readln().toFloat()
                            if (pricePerKg <= 0f) println("Delivery price must be more than 0!!! Please enter valid price: ")
                        } while(pricePerKg <= 0f)

                        couriers.add(Courier(courierName, pricePerKg))
                        println("Courier is added successfully. You can add package to this courier. Courier name: $courierName")
                    }

                    // Delete the courier permanently
                    2 -> {

                        var i = 0
                        for (row in couriers) {
                            i++
                            println("$i. ${row.getCourierName()}")
                        }
                        println("0. Back to main menu")

                        print("Which one do you want to delete? Enter a number: ")
                        do {
                            option = readln().toInt()
                            if(option !in 0 .. i)
                                print("Please enter a valid number: ")
                        } while (option !in 0..couriers.size)

                        if(option == 0) continue // Back to main menu

                        if (couriers[option as Int - 1].packages.size != 0 ) {
                            if (couriers[option - 1].packages.size == 1)
                                println("The courier you want to delete has a package so it cannot be deleted.\n" +
                                        "Firstly, this courier has to deliver this package!")
                            else
                                println("The courier you want to delete has packages so it cannot be deleted.\n" +
                                        "Firstly, this courier has to deliver these packages!")
                            continue
                        }

                        println("${couriers[option - 1].getCourierName()} courier is deleted successfully.")
                        couriers.removeAt(option - 1)
                    }

                    // Get total revenue of a courier
                    3 -> {
                        println("1. Show revenues of all couriers")
                        println("2. Show revenue of a courier")
                        println("0. Back")

                        print("Enter a number: ")
                        do {
                            option = readln().toInt()
                            if (option !in 0..2) print("Please enter a valid number: ")
                        } while (option !in 0 .. 2)

                        when(option) {
                            0 -> continue // Back

                            // Show revenues of all couriers
                            1 -> {
                                for (row in couriers) {
                                    println("Courier: ${row.getCourierName()} -> Revenue: ${row.getTotalRevenue()}")
                                }

                                var x: String
                                print("Do you want to print it? (Y/n)")
                                do {
                                    x = readln()
                                } while (x != "Y" && x != "n")

                                condition = x == "Y"
                                if (condition) println("Printed...")
                                println("Please press Enter to back")
                                readln()
                            }

                            2 -> {
                                println("Enter courier's name you want to see")

                                condition = true
                                courierName = readln()
                                for (row in couriers) {
                                    if (row.getCourierName() == courierName) {
                                        println("Courier: ${row.getCourierName()} ->" +
                                                " Revenue: ${row.getTotalRevenue()}")
                                        condition = false

                                        println()
                                        println("Please press Enter to back")
                                        readln()
                                        break
                                    }
                                }
                                if(condition) {
                                    println("It isn't correct name.")
                                    continue
                                }


                                var x: String
                                print("Do you want to print it? (Y/n)")
                                do {
                                    x = readln()
                                } while (x != "Y" && x != "n")

                                condition = x == "Y"
                                if (condition) println("Printed...")

                            }
                        }
                    }

                    // Package Information
                    4 -> {
                        println("1. Show all packages:")
                        println("2. Show a package")
                        println("0. Back")

                        print("Enter a number: ")
                        do {
                            option = readln().toInt()
                            if (option !in 0..2) print("Please enter a valid number: ")
                        } while (option !in 0 .. 2)

                        when(option) {
                            // Back
                            0 -> continue

                            // Show all packages
                            1 -> {
                                for(row in couriers) {
                                    for(col in row.packages) {
                                        println("Package : ${col.getPackageName()} ")
                                        println("   Sender: ${col.getSender()}")
                                        println("   Recipient: ${col.getRecipient()}")
                                        println("   Price: ${col.getPrice()}")
                                        println("   Weight: ${col.getWeight()}")
                                        println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}") }")
                                        println("   Courier: ${row.getCourierName()}")
                                        println("   Tracking number: ${col.getTrackingNumber()}")
                                        println()
                                    }
                                    println("\n")
                                }
                            }

                            // Show a package
                            2 -> {
                                var i = 1
                                for (row in couriers) {
                                    for (col in row.packages){
                                        println("$i. ${col.getPackageName()}: #${col.getTrackingNumber()}")
                                        i++
                                    }
                                }
                                println("0. Back")
                                print("Enter a number: ")
                                do {
                                    option = readln().toInt()
                                    if (option !in 0 until i)
                                        print("Please enter a valid number: ")
                                } while (option !in 0 until i)

                                if(option == 0) continue // Back

                                i = 1
                                seconder@for (row in couriers) {
                                    for (col in row.packages) {
                                        if (i == option) {
                                            println("Package : ${col.getPackageName()} ")
                                            println("   Sender: ${col.getSender()}")
                                            println("   Recipient: ${col.getRecipient()}")
                                            println("   Price: ${col.getPrice()}")
                                            println("   Weight: ${col.getWeight()}")
                                            println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}") }")
                                            println("   Courier: ${row.getCourierName()}")
                                            println("   Tracking number: ${col.getTrackingNumber()}")
                                            println()
                                            break@seconder
                                        }
                                        i++
                                    }
                                }
                            }
                        }
                    }

                    // Package Operations
                    5 -> {
                        var i: Int = 1
                        for(row in couriers) {
                            for(col in row.packages) {
                                println("$i. Package : ${col.getPackageName()} , #${col.getTrackingNumber()}")
                                i++
                            }
                            println("\n")
                        }
                        println("0. Back")

                        var packageOption: Int
                        print("Enter a number: ")
                        do {
                            packageOption = readln().toInt()
                            if (packageOption !in 0 until i)
                                print("Please enter a valid number: ")
                        } while (packageOption !in 0 until i)

                        if(packageOption == 0) continue // Back

                        println("1. Update Package Step")
                        println("2. Deliver the package")
                        println("0. Back")
                        print("Enter a number: ")
                        do {
                            option = readln().toInt()
                            if (option !in 0..2)
                                print("Please enter a valid number: ")
                        } while (option !in 0..2)

                        when(option) {
                            0 -> continue //Back

                            // Update Package Step
                            1 -> {
                                i = 1
                                for(row in couriers) {
                                    for(col in row.packages) {
                                        if(packageOption == i) {
                                            val step = col.step.ordinal
                                            col.updatePackageStep(step + 1)

                                            println("Package step is updated.")
                                            println("Current step: ${col.step.name}")
                                            break
                                        }
                                        i++
                                    }
                                }
                            } // 1

                            2 -> {
                                i = 1
                                for(row in couriers) {
                                    for(col in row.packages) {
                                        if(packageOption == i) {
                                            val step = col.step.ordinal
                                            col.deliverPackage()
                                            println("Package is delivered.")
                                            println("Current step: ${col.step.name}")
                                            break
                                        }
                                        i++
                                    }
                                }
                            } //2

                        } // when

                    } // Package Operation : 5

                    else -> println("\nelse\n") // never should be
                }

            }
        }


//        println("8.1 Get weight of the package")
//        println("8.2 Get delivery time of the package")
//        println("8.3 Get estimated delivery time of the package")
//        println("8.4 Track the package")
//        println("9.3 Calculate delivery cost")
//        TARIFFS


        println("\nPROCESS ENDED\n")
    }

}

fun YorN(message: String): Boolean {
    val sure: Boolean
    var x: String
    do{
        print("Enter Y/n: ")
        x = readln()
    } while(x != "Y" && x != "n")
    sure = x == "Y"

    if(!sure) {
        println(message)
        return false
    }
    return true
}

fun calculateWeight(): Float {
    val rangeStart = 0.1f
    val rangeStop = 101f
    return String.format("%.3f", Random.nextFloat() * (rangeStop - rangeStart) + rangeStart).toFloat()
}

fun setTrackingNumber(): String = Random.nextInt(1000, 10000).toString()

fun enterNumber(x: Int, y: Int) : Int {
    var option: Int
    print("Enter a number: ")
    do {
        option = readln().toInt()
        if (option !in x..y) print("Please enter a valid number: ")
        else return option
    } while (true)
}

fun enterNumber(x: Int, y: Int, message: String) : Int {
    var option: Int
    println(message)
    do {
        option = readln().toInt()
        if (option !in x..y) print("Please enter a valid number: ")
        else return option
    } while (true)
}

interface PackageDelivery {
    fun getEstimatedDeliveryTime(distance: Int): String
    fun deliverPackage()
}