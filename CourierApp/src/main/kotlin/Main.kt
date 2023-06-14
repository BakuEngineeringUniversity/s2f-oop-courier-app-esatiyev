import kotlin.random.Random

fun main(args: Array<String>) {

    var users = ArrayList<User>()
    val user1 = User("Elton", "Satiyev", 18, "Male", "0100",
        "a", "a", "82tt", "a")
    users.add(user1)

    var USER: User
    var uNumber = 0

    var packageName: String
    val sizeOfPackageType = 6

    var couriers = ArrayList<Courier>()
    var courierName: String

    var recipient: String
    var sender: String
    var price: Float
    var weight: Float
    var trackingNumber: String

    var option: Int
    var condition: Boolean = true

    while(true){
        println("1. Log into app as user")
        println("2. Log into app as admin")
        option = enterNumber(1, 2)


        if (option == 1) {
            println("1. Log in")
            println("2. Register")
            println("0. Back")

            when(enterNumber(0, 2)) {
                0 -> continue  // Back

                // Log in
                1 -> {
                    val (uNumber1, condition1) = login(users)
                    uNumber = uNumber1
                    condition = condition1
                }

                // Register
                2 -> {
                    users = register(users)
                    println(users[users.size - 1].getName())

                    for (row in couriers) {
                        row.createUser(users[users.size - 1])  // son elave edilen useri courierlere elave edir
                    }
                    continue
                }
            }
        }

        if(!condition) continue // eger 0 secib back deyirsə işləyir

        USER = users[uNumber]  //USER teyin edildi
        when (option) {
            // User Interface
            1 -> {
                for(row in couriers) {
                    for (col in row.users) {
                        if (col.getEmail() == USER.getEmail()) {
                            for (th in col.packages) {
                                if(th.step.name == "DELIVERED" && th.getRate() == 0) {
                                    couriers = rateDelivery(th.getTrackingNumber(), USER.getEmail(), couriers)
                                    for (i in USER.packages) {
                                        if(i.getTrackingNumber() == th.getTrackingNumber()) {
                                            i.setRate(th.getRate())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                println("1. Create New Package")  // user
                println("2. My Packages") // user
                println("3. Profile")
                println("4. Calculator & Tariffs")
                println("0. Sign out")

                when(enterNumber(1, 5)) {
                    // Create a new package
                    1 -> {
                        println("Choose your package type")
                        println("1: Regular Package")
                        println("2: Express Package")
                        println("3: Fragile Package")
                        println("4. Oversized Package")
                        println("5. Hazardous Package")
                        println("6. Perishable Package")
                        println("0. Back")

                        val packageType: Int = enterNumber(0, sizeOfPackageType, "Choose your package type")

                        if (packageType == 0) continue

                        println("Enter package name: ")
                        packageName = readln()


                        println("Enter sender name: ")
                        sender = readln()

                        println("Enter price of your product: ")
                        price = readln().toFloat()

                        recipient = USER.getName() + " " + USER.getSurname()
                        weight = calculateWeight()
                        trackingNumber = setTrackingNumber()

                        println("1. Standart Delivery: 7-10 days")
                        println("2. Express Delivery: 2-4 days")
                        println("3. Overnight Delivery: 1-2 days")
                        println("0. Back")

                        var deliveryMethod: DeliveryMethod = DeliveryMethod.Express
                        if (packageType != 2){
                            option = enterNumber(0, 3, "Select a delivery method!")
                            if (option == 0) continue
                            deliveryMethod = if (option == 1) DeliveryMethod.Standart
                            else if (option == 2) DeliveryMethod.Express
                            else DeliveryMethod.Overnight
                        }

                        when(packageType){
                            // Regular Package
                            1 -> {
                                USER.addPackage(RegularPackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod))

                                println("Regular Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Express Package
                            2 -> {
                                USER.addPackage(ExpressPackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod ))
                                println("Express Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Fragile Package
                            3 -> {
                                val isFragile: Boolean = YorN("Is package fragile? (Y/n)")

                                USER.addPackage(FragilePackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod, isFragile))
                                println("Fragile Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Oversized Package
                            4 -> {
                                USER.addPackage(OversizedPackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod))

                                println("Regular Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Hazardous Package
                            5 -> {
                                USER.addPackage(HazardousPackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod))

                                println("Hazardous Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            // Perishable Package
                            6 -> {
                                USER.addPackage(PerishablePackage(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod))

                                println("Perishable Package is added successfully. You can track it with tracking number: $trackingNumber")
                            }
                            else -> {
                                println("Invalid package type!!!")  // never should be
                            }
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

                        var i = 1
                        for(row in USER.packages) {
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

                                secondfor@for (row in couriers) {
                                    for (col in row.packages) {
                                        if (col.getTrackingNumber() == USER.packages[option - 1].getTrackingNumber()) {
                                            println("This package is already added to ${row.getCourierName()} cargo." +
                                                    "by ${col.getRecipient()}")
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
                                if(courierNumber == 0) continue

                           //     println("Delivery time: " + USER.packages[option-1].getDeliveryTimeFormatted() )

                                couriers[courierNumber - 1].addPackage(USER.packages[option - 1], USER)
                                println("Package is added to ${couriers[courierNumber - 1].getCourierName()} courier company.")
                            }

                            // Remove the package from current courier
                            2 -> {
                                option = enterNumber(0, i - 1, "Choose a package")
                                if (option == 0) continue

                                println("Do you really want to remove this package from this cargo company?")

                                if (!YorN("Package isn't removed!")) continue

                                // check that this package is already added to any courier or not, if not then "continue"
                                var breakExistPackage: Boolean = true

                       //         if (couriers.isNotEmpty())
                                secondfor@for (row in couriers) {
                                    //  if (row.packages.isEmpty()) continue
                                    for (col in row.users) {
                                        if (col == USER)
                                            for (th in col.packages){
                                                if (th.getTrackingNumber() == USER.packages[option -1].getTrackingNumber()) {
                                                    row.removePackage(th, USER)
                                                    println("Package is removed successfully.")
                                                    breakExistPackage = false
                                                    break@secondfor
                                                }

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

                                option = enterNumber(1, 4, "Select a option: ")

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

//                                if (couriers.isNotEmpty())
                                    secondfor@for (row in couriers) {
//                                        if (row.packages.isNotEmpty())
                                            for (col in row.packages) {
                                                if (col.getTrackingNumber() == USER.packages[option - 1].getTrackingNumber()) {
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

                                USER.packages.removeAt(option - 1)
                                println("Package is deleted successfully.")
                            }

                            // Show package information in couriers
                            4 -> {
                                println("1. Show all packages in couriers:")
                                println("2. Show a package in couriers")
                                println("0. Back")

                                when(enterNumber(0, 2)) {
                                    // Back
                                    0 -> continue

                                    // Show all packages in couriers
                                    1 -> {
                                        for(row in couriers) {
                                            for(col in row.users) {
                                                if (col.getEmail() == USER.getEmail()) {
                                                    for (th in col.packages) {
                                                        condition = true
                                                        println("Package : ${th.getPackageName()}")
                                                        println("   Recipient: ${th.getRecipient()}")
                                                        println("   Sender: ${th.getSender()}")
                                                        println("   Price: ${th.getPrice()}")
                                                        println("   Weight: ${th.getWeight()}")
                                                        println("   Delivery cost: ${ row.calculateDeliveryCost("${th.getTrackingNumber()}", USER)}")
                                                        println("   Courier: ${row.getCourierName()}")
                                                        println("   Tracking number: ${th.getTrackingNumber()}")
                                                        println("   Estimated Delivery Time: ${th.getEstimatedDeliveryTime()}")
                                                        println()
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    // Show a package
                                    2 -> {
                                        i = 1
                                        for (row in couriers) {
                                            for (col in row.users) {
                                                if(col.getEmail() == USER.getEmail()) {
                                                    for (th in col.packages) {
                                                        println("$i. ${th.getPackageName()}: #${th.getTrackingNumber()}")
                                                        i++
                                                    }
                                                }
                                            }
                                        }

                                        println("0. Back")

                                        option = enterNumber(0, i - 1)

                                        if (option == 0) continue // Back

                                        USER.packages[option-1].getTrackingNumber()
                                        i = 1
                                        seconder@for (row in couriers) {
                                            for (col in row.users)
                                                if (col.getEmail() == USER.getEmail()) {
                                                    for (th in col.packages) {
                                                        if (th.getTrackingNumber() ==  USER.packages[option-1].getTrackingNumber()) {
                                                            println("Package : ${th.getPackageName()}")
                                                            println("   Recipient: ${th.getRecipient()}")
                                                            println("   Sender: ${th.getSender()}")
                                                            println("   Price: ${th.getPrice()}")
                                                            println("   Weight: ${th.getWeight()}")
                                                            println("   Delivery cost: ${ row.calculateDeliveryCost("${th.getTrackingNumber()}", USER) }")
                                                            println("   Courier: ${row.getCourierName()}")
                                                            println("   Tracking number: ${th.getTrackingNumber()}")
                                                            println("   Estimated Delivery Time: ${th.getEstimatedDeliveryTime()}")
                                                            println()
                                                            break@seconder
                                                        }
                                                        i++
                                                    }

                                                }
                                        }
                                    } // end show a package
                                }
                            } // End Show Package
                        }


                    }

                    // Profile
                    3 -> {
                        println("Profile\n")
                        println("Name: " + USER.getName())
                        println("Surname: " + USER.getSurname())
                        println("E-mail: " + USER.getEmail())
                        println("Phone: " + USER.getPhone())  // changeable
                        println("Gender: " + USER.getGender())
                        println("Address: " + USER.getAddress()) // changeable
                        println("Personal No: " + USER.getPersonalNo())

                        println("1. Change the password")
                        println("2. Change the phone number")
                        println("3. Change the address")
                        println("0. Back")

                        when (enterNumber(0, 3)) {
                            0 -> continue // Back

                            // Change the password
                            1 -> {
                                users = changePassword(users, uNumber)
                                USER = users[uNumber]
                            }

                            // Change the phone number
                            2 -> {
                                users = changePhoneNumber(users, uNumber)
                                USER = users[uNumber]
                            }

                            // Change the address
                            3 -> {
                                users[uNumber].setAddress(readln())
                                USER = users[uNumber]
                            }
                        }
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
                println("0. Sign out")

                option = enterNumber(0, 5)

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

                        for (row in users) {
                            couriers[couriers.size - 1].createUser(row)  // en son elave edilen couriere butun userleri elave edir
                        }
                    }

                    // Delete the courier permanently
                    2 -> {

                        var i = 0
                        for (row in couriers) {
                            i++
                            println("$i. ${row.getCourierName()}")
                        }
                        println("0. Back to main menu")

                        option = enterNumber(0, couriers.size, "Which one do you want to delete? Enter a number: ")

                        if(option == 0) continue // Back to main menu

                        if (couriers[option - 1].packages.size != 0 ) {
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

                        option = enterNumber(0, 2)

                        when(option) {
                            0 -> continue // Back

                            // Show revenues of all couriers
                            1 -> {
                                for (row in couriers) {
                                    println("Courier: ${row.getCourierName()} -> Revenue: ${row.getTotalRevenue()}")
                                }

                                condition = YorN("Do you want to print it? (Y/n)")
                                if (condition) println("Printed...")

                                println("Please press Enter to back")
                                readln()
                            }

                            // Show revenue of a courier
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

                                condition = YorN("Do you want to print it? (Y/n)")
                                if (condition) println("Printed...")

                            }
                        }
                    }

                    // Package Information
                    4 -> {
                        println("1. Show all packages:")
                        println("2. Show a package")
                        println("0. Back")

                        enterNumber(0, 2)

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
                                        println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}", USER) }")
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
                                            println("   Delivery cost: ${ row.calculateDeliveryCost("${col.getTrackingNumber()}", USER) }")
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

                        var packageOption: Int = enterNumber(0, i - 1)

                        if(packageOption == 0) continue // Back

                        println("1. Update Package Step")
                        println("2. Deliver the package")
                        println("0. Back")

                        option = enterNumber(0, 2)

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

                            // Deliver Package
                            2 -> {
                                i = 1
                                for(row in couriers) {
                                    for(col in row.packages) {
                                        if(packageOption == i) {
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

fun rateDelivery(trackingNumber: String, email: String, couriers: ArrayList<Courier>): ArrayList<Courier> {
    var option: Int = 0
    for (row in couriers) {
        // couriers.users.packages rate
        for (col in row.users) {
            if(col.getEmail() == email) {
                for (th in col.packages) {
                    if(th.getTrackingNumber() == trackingNumber) {
                        println("Rate us for better service:")
                        println("1. 1 point 😕")
                        println("2. 2 points🙁")
                        println("3. 3 points😑")
                        println("4. 4 points🙂")
                        println("5. 5 points😀")

                        option = enterNumber(1, 5)
                        println("Thanks for rating!")
                        th.setRate(option)
                    }
                }
            }
        }

        // couriers.packages rate
        for (col in row.packages) {
            if(col.getTrackingNumber() == trackingNumber) {
                col.setRate(option)
            }
        }
    }

    return couriers
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
    } while (option !in x..y)
    return option
}

fun login(list: ArrayList<User>): Pair<Int, Boolean> {
    var uNumber = 0
    var condition: Boolean = true
    do {
        print("Email: ")
        val loggedName: String = readln()
        print("Password: ")
        val loggedPassword: String = readln()
        var auth = false

        for((i, row) in list.withIndex()) {
            if (loggedName == row.getEmail() && loggedPassword == row.getPassword()) {
                println("Hi,${row.getName()}.You are already login in.")
                uNumber = i  // USERin users listinde hansi oldugunu teyin etmek ucundur
                auth = true
                condition = true
            }
        }

        if (!auth) {
            println("Username or password is incorrect!!!")
            println("1. Log in")
            println("0. Back")
            if (enterNumber(0, 1) == 0){
                condition = false
                break
            }
        }
    } while (!auth)

    return Pair(uNumber, condition)
}

fun register(list: ArrayList<User>): ArrayList<User> {
    println("Hi,please fill below.")
    print("Name: ")
    val name: String = readln()
    print("Surname: ")
    val surname: String = readln()
    print("Age: ")
    val age: Int = readln().toInt()
    print("Gender: ")
    val gender: String = readln()
    print("Phone Number: ")
    val phone: String = readln()
    print("Address: ")
    val address: String = readln()
    print("Personal No: ")
    val personalNo: String = readln()
    print("E-mail: ")
    val email: String = readln()
    print("Password: ")
    val password: String = readln()

    list.add(User(name, surname, age, gender, phone, email, address, personalNo, password))
    println("Account is created successfully. Please login!")

    return list
}

fun changePassword(list: ArrayList<User>, uNumber: Int): ArrayList<User> {
    print("Enter old password: ")
    var oldP: String
    do {
        oldP = readln()
        if (oldP != list[uNumber].getPassword()) {
            println("Password isn't correct")

            print("Do you want to continue? Y/n: ")
            if (!YorN("Password isn't changed"))  return list

            print("Enter old password: ")
        }
    } while (oldP != list[uNumber].getPassword())

    print("Enter new password: ")
    val newP = readln()
    list[uNumber].setPassword(newP)
    println("Password is changed successfully!")

    return list
}

fun changePhoneNumber(list: ArrayList<User>, uNumber: Int): ArrayList<User> {
    var newPhone: String
    println("Ex: +994-XX-XXX-XX-XX)")
    print("Enter new phone number: +994")
    do {
        newPhone = readln()
        if (newPhone.length != 9) {
            println("Phone number isn't correct!")

            print("Do you want to continue? Y/n: ")
            if (!YorN("Phone number isn't changed"))  return list

            print("Enter a valid phone number: +994")
        }
    } while (newPhone.length != 9)

    list[uNumber].setPhone("+994$newPhone")
    println("Phone number is changed successfully!")
    return list
}