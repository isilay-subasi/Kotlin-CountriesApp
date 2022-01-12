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
layout_weight özelliğini kullanacağız.