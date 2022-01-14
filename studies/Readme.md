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
## Adapter
+ Adapter paketi oluşturup içerisine CountryAdapter adında sınıf oluşturuyoruz.
Bu sınıf parametre olarak bir ArrayList alacak ve Country modeli şeklinde.
Ve bize bir adapter döndürecektir.
```
RecyclerView.Adapter<CountryAdapter.CountryViewHolder>()
```
+ Yukarıdaki değer de bizden bir ViewHolder sınıfı istiyor. O sınıfı oluşturuyoruz.
```

    class CountryViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }
 ```
 + Parametre olarak view elemanı veriyoruz. Ve bize ViewHolder döndürecektir.

 + implement etmem gereken metotları implement ediyorum.

## ViewModel ve LiveData

+ Her view'ın ayrı bir viewmodeli olması gerekiyor. Clean code yazmak amaçlı bir mimari yapı olduğu için fragmentler için ayrı ayrı viewmodel oluşturacağız.

+ MVVM kullanmanın diğer bir artısı veya özelliği lifecycle. Normal bir fragmentte veya aktivitede oncreate gibi başlarken oluşan metotlar , durdurulurken cihazı yana çevirdiğimiz çalışacak metotlar vardır. 
Ama ViewModel'ın bir tane lifecycle vardır. İster yan çevir ister başka şeyler yap sadece tek bir scopeda gösteriliyor. onCleared() bitince belli eden bir metotdur.

+ LiveData (Canlı Veri) -> Observable olması. Yani gözlemlenebilir olması. <br>
Observer - Gözlemci demek. Bizde gözlemlenebilir veriler oluşturacağız. Bu veriye observerların yani gözlemcilerin bu veriye her zaman erişimi sağlanıyor. 

+ ViewModelimiz oluşturduk ve içerisindeki datalarımızı livedata olarak kullanacağız.

```
class FeedViewModel : ViewModel(){

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    
}
```
## ViewModel Oluşturmak

+ refreshData() metodu oluşturuyoruz. Bunu kullanarak ilerde datalarımızı çekeceğiz ve refresh edeceğiz. Oluşturduktan sonra View tarafını yazmaya geçeceğiz.FeedFragment içerinde viewmodelimizi oluşturacağız.


## Retrofit ve RxJava Nedir ? 

+ Retrofit -> İnternetten veri indirirken , özellikle apiyle çalışırken GET,POST,UPDATE gibi işlemleri yapmamızı sağlayan bir kütüphanedir.Arka planda indirmesi, asenkron çalışması, kullanıcının uı bloklamaması önemli özelliktir.

+ RxJava -> Yine çok güçlü bir yardımcı kütüphanedir.Rxjavayı kullanarak gözlemlenebilir yapabiliriz.Başka threadlerde yapam gibi başka araçlara sahip olabiliriz. 

+ Basit işlemler için Retrofiti tercih ederken daha kapsamlı uygulamalarda RxJavayı tercih etmeliyiz. 

### Interface Yazmak 

>Öncelikle interface oluşturuyoruz. Ve burda hangi işlemleri yapacağımızı söylüyoruz.GET,POST  (CountryAPI)

>Uygulamamızda verileri indireceğiz. Ve indirdikten sonra yerel veritabanımıza kaydedeceğiz. Şöyle bir algoritma izleyeceğiz. Eğer 10dk geçtiyse tekrar apiden indir. Geçmediyse halihazırda sqlitedan kullanabilirsin. 

> Call işlemleri için rxjavda single özelliğini kullanıyoruz. Observabla diye gözlemlenebilir call de vardır. Değişk call çağrılıarı vardır. 

### Servis Yazmak 

> Servisimizi yazmamız retrofit objemizi oluşturmamız gerekiyor. 


```
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getData() : Single<List<Country>>{
        return api.getCountries()
    }
```


### Verileri Almak

+ Verileri almak için viewmodelimiz içerisinde servisimizin objesini oluşturmalıyız.

```
    //servis objemizi oluşturmamız gerekiyor.
    private val countryApiService =CountryAPIService()
    //disponsablemızı oluşturacağız.
    private val disposable = CompositeDisposable()
```

> CompositDisposable() -> Biz bu tarz Call'lar yaparken , internetten veri indirirken Retrofitle ya da RxJava ile her yaptığımız Call hafızada bir yer tutuyor. Fragmentler clear edildiğinde ya da başka bir fragmente geçildiğinde kapatıldıgında bu Callardan kurtulmamız gerekiyor. Kurtulmazsak eğer hafızada yer ediyor ve sıkıntılara sebep olabiliyor. CompositDisposibla() tam olarak bu işe yarıyor. Bir tane büyük bir obje oluşturuyor. Call yaptıkça internetten veri indirdikçe bunun içerisine atıyoruz. disposable (Kullan at demek ). Bu kullan at objemize bunları dolduruyoz. En sonunda temizleyerek hafızamızı verimli kullanabiliyoruz. Bu çok faydali bir şeydir. Birden fazla Call işlemi yaparsak hepsini bunun içerisine koyuyoruz. 


### Refresh Layout

```
        //Kullanıcı refresh ettiğinde ne olacak ?
        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility=View.GONE
            countryError.visibility=View.GONE
            countryLoading.visibility=View.VISIBLE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing=false
        }
```
