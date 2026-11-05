package com.example.footballcareer
import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class Player(var first:String="Lelé",var last:String="Silva",var pos:String="LF",
 var ovr:Int=47,var potential:Int=82,var age:Int=17,var club:String="FC Erlangen",
 var form:Double=6.8,var fitness:Int=94,var morale:Int=75,var goals:Int=0,var assists:Int=0,var apps:Int=0)

class MainActivity:Activity(){
 private val bg=Color.rgb(9,13,24); private lateinit var root:LinearLayout
 private var p=Player(); private var date=Calendar.getInstance()
 private var news=mutableListOf("Willkommen in deiner neuen Karriere.")
 private var last="Noch kein Spiel gespielt."
 override fun onCreate(b:Bundle?){super.onCreate(b);home()}
 private fun base()=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg);setPadding(22,18,22,18)}
 private fun t(s:String,n:Float=16f,b:Boolean=false)=TextView(this).apply{text=s;textSize=n;setTextColor(Color.WHITE);if(b)typeface=Typeface.DEFAULT_BOLD;setPadding(0,8,0,8)}
 private fun b(s:String,a:()->Unit)=Button(this).apply{text=s;isAllCaps=false;setOnClickListener{a()}}
 private fun put(v:LinearLayout){root=v;setContentView(root)}
 private fun gap()=Space(this).apply{minimumHeight=16}
 private fun home(){val l=base();l.addView(t("FOOTBALL CAREER",30f,true));l.addView(t("Deine Karriere. Deine Entscheidungen. Eine lebendige Fußballwelt.",15f));l.addView(gap())
  l.addView(b("⚽ Spielerkariere"){hub()});l.addView(b("🧠 Trainerkarriere"){toast("Als nächstes: vollständige Trainerkarriere.")})
  l.addView(b("🌍 Fußballwelt"){world()});l.addView(b("⚙ Einstellungen"){toast("Einstellungen folgen.")});put(l)}
 private fun hub(){val l=base();l.addView(t("KARRIERE-HUB",14f,true));l.addView(t("${p.first} ${p.last}",28f,true))
  l.addView(t("${p.pos} • OVR ${p.ovr} • ${p.age} Jahre\n${p.club}\nForm ${"%.1f".format(p.form)} | Fitness ${p.fitness}% | Moral ${p.morale}%"))
  l.addView(gap());l.addView(b("📅 Kalender"){calendar()});l.addView(b("🏋 Training"){train()});l.addView(b("⚽ Nächstes Spiel simulieren"){match()});l.addView(b("👤 Spielerprofil"){profile()});l.addView(b("📰 News"){news()});l.addView(b("🌍 Welt"){world()});l.addView(b("← Hauptmenü"){home()});put(l)}
 private fun train(){p.fitness=max(55,p.fitness-4);p.form=min(9.9,p.form+.08);if(Random.nextDouble()<.35&&p.ovr<p.potential)p.ovr++;news.add(0,"Training: ${p.first} arbeitet an seiner Entwicklung.");date.add(Calendar.DAY_OF_YEAR,1);hub()}
 private fun match(){val opp=listOf("SC Bamberg","SV Fürth","FC Nürnberg II","SpVgg Ansbach").random();val gf=Random.nextInt(0,4);val ga=Random.nextInt(0,3);p.apps++;p.fitness=max(45,p.fitness-12);val r=(6+(p.ovr+Random.nextInt(-8,9)-Random.nextInt(42,61))/18.0+Random.nextDouble(-.7,.7)).coerceIn(4.5,9.8);p.form=(p.form*.75+r*.25).coerceIn(1.0,10.0);if(gf>0)p.goals+=min(gf,2);if(Random.nextDouble()<.35)p.assists++;last="${p.club} $gf : $ga $opp\nDeine Bewertung: ${"%.1f".format(r)}";news.add(0,"Spieltag: ${p.club} spielt $gf:$ga gegen $opp.");date.add(Calendar.DAY_OF_YEAR,2);showMatch()}
 private fun showMatch(){val l=base();l.addView(t("MATCHDAY",14f,true));l.addView(t(last,24f,true));l.addView(t("Einsätze ${p.apps} • Tore ${p.goals} • Assists ${p.assists}"));l.addView(gap());l.addView(t("MATCHSIMULATION\nBallbesitz • Chancen • Pässe • Dribblings • Zweikämpfe • xG\n\nDer Simulationskern ist spielbar. Die animierte 2D-Pitch-Engine ist das nächste große Modul.",15f));l.addView(b("Weiter"){hub()});put(l)}
 private fun profile(){val l=base();l.addView(t("SPIELERPROFIL",14f,true));l.addView(t("${p.first} ${p.last}",28f,true));l.addView(t("LF • ${p.age} Jahre • OVR ${p.ovr} • Potential ${p.potential}"));l.addView(t("Form ${"%.1f".format(p.form)} | Fitness ${p.fitness}% | Moral ${p.morale}%"));l.addView(t("\nATTRIBUTE",14f,true));l.addView(t("Ballkontrolle 62   Dribbling 65\nKurzpass 55       Vision 49\nAbschluss 43      Sprint 67\nAntritt 70        Ausdauer 58\nBalance 71        Stärke 42\nTackling 28       Stellungsspiel 51"));l.addView(t("\nKARRIERE",14f,true));l.addView(t("Einsätze ${p.apps} • Tore ${p.goals} • Assists ${p.assists}"));l.addView(b("← Zurück"){hub()});put(l)}
 private fun calendar(){val l=base();val f=SimpleDateFormat("EEEE, dd.MM.yyyy",Locale.GERMAN);l.addView(t("KALENDER",14f,true));l.addView(t(f.format(date.time),26f,true));l.addView(t("Mo Training\nDi Training\nMi Mannschaftstraining\nDo Gespräch/Regeneration\nFr Kaderbekanntgabe\nSa Spieltag\nSo Regeneration"));l.addView(b("Tag weitersimulieren"){date.add(Calendar.DAY_OF_YEAR,1);calendar()});l.addView(b("← Zurück"){hub()});put(l)}
 private fun news(){val l=base();l.addView(t("FUSSBALLWELT • NEWS",14f,true));news.take(12).forEach{l.addView(t("• $it",15f))};l.addView(b("← Zurück"){hub()});put(l)}
 private fun world(){val l=base();l.addView(t("FUSSBALLWELT",14f,true));l.addView(t("2026/27",28f,true));l.addView(t("Autonome Systeme für Clubs, Spieler, Ligen, Transfers, Nationalteams und Geschichte werden modular aufgebaut.\n\n🇩🇪 Deutschland • 🇬🇧 England • 🇪🇸 Spanien • 🇮🇹 Italien • 🇫🇷 Frankreich\n🌍 Weitere Länder erweiterbar"));l.addView(b("📰 Welt-News"){news()});l.addView(b("← Zurück"){hub()});put(l)}
 private fun toast(s:String)=Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
}