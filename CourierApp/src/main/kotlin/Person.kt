abstract class Person(
    private var name: String,
    private var surname: String,
    private var age: Int,
    private var gender: String,
    private var phone: String,
    private var email: String
) {
    fun getName(): String = name

    fun getSurname(): String = surname

    fun getAge(): Int = age

    fun getGender(): String = gender

    fun getPhone(): String = phone
    fun getEmail(): String = email
}

/*
a yaradilib sonra courier yaradildi, paket yaradildi,
 show allda yaradilan paket gorsenir, //eslinde gorsetmemelidir elave edilmeyib
 show a paketde gorsenmir //bu bele duzdur
 paketi elave edende eyni paket tezeden yaranir 2 paket olur //bu eyni trackla olmamalidir
 show allda her iki paket gorsenir //duzdur
 elave edilen paketi show a paket etdikde gorsetmir, amma evvelki paket gorsenir
 3cu paket yaradildi,elave edilmedi,amma show allda gorsedir, show ada gorsetmir
 paket elave etdim show allda yeniden 2 eded gorsenir

 */