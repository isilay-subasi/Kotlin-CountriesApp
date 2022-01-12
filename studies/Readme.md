# Countries App

<h2>Kotlin : Layout</h2>

->Feed Fragment UI<br>
->Yüzdesel Ağırlıklarla Çalışmak<br>
->Ülke Sayfası<br>

<hr>

SwipeRefreshLayout kullanacağız.
Aşağı doğru çekince güncelleme yapabilmek için bu layoutu kullanıyoruz.
feed_fragment.xml kısmına RecyclerView,Progress Bar ve SwipeRefreshLayoutumuzu ekledik.

RecyclerView itemlerinın görünümlerini ayarlamak için item_country.xml oluşturuyoruz.
İçerindeki itemları yaparken responsif tasarım için imageview içerindeki boyutlarda 
<b>layout_weight</b> özelliğini kullanacağız.
<hr>

<h2>Kotlin : MVVM MIMARI YAPISI</h2>

->MVVM Nedir?<br>
->Modeli Yazmak<br>
->Adapter Yazımı<br>
->ViewModel ve LiveData<br>
->ViewModel Oluşturmak<br>
->Verileri Göstermek<br>
->Country ViewModel<br>

<hr>

İstek yapacağımız apiden dönecek jsonunun içerisinde aşağıdaki gibi veriler olacaktır.

```
{
    "name" : "Algeria"
    "capital" : "Algiers"
    "region" : "Africa"
    "flag" : "resim_url"
    "language" : "Arabic"

}
```


 Bu verileri alacağımız bir model oluşturacağız.Modelden sonra MVVM dedidiğimiz bir mimari yapıda kodlarımızı yazacağız.(Model-View-viewmodel)<br>
><b>MVVM :</b> 3 ana bileşen vardır. Model,View,View Model.Business mantıkla kullanıcı arayüzü mantığıın birbirinden ayırmak için kullanılan bir mimari yapıdır.
Gelen datanın işlenmesi ,arka planda gösterilmesi , arka planda yapılacak işlemler olsun , database işlemleri olsun bunun gibi kullanıcının ne olduğuyla hiç bir ilgisi olmayan işlemleri biz ViewModel de yapacağız.
Kullanıcının göreceği, arayüzü ilgilendiren işlemleri View içerisinde yapacağız.
Ve tüm bunlar modelden beslenecek.

+ View üzerindeki fazla koddan kurtulacağız.
+ Fonksiyonlarla uğraştığım tüm işlermleri ViewModelde yapacağım. Ve modelleyerek yapacağım.
Modelden beslenecek.
+ Test yapmak daha kolay olacak.
+ Kodlar daha temiz olacaktır. 


## Model 

+ Model paketiniz içerisinde Model adında bir kotlin file açıyoruz.
Ve içerisine data class() yazıyoruz. Data Class'ın farkı verilerin tutulduğu , verilerin modellenmesi için böyle bir sınıf oluşturulmuştur.

```
data class Country(
    val countryName : String?,
    val countryRegion : String?,
    val countryCapital : String?,
    val countryCurrency : String?,
    val countryLanguage : String?,
    val imageUrl : String?,

)
```
