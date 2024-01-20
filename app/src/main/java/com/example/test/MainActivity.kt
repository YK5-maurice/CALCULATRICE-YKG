package com.example.test
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException
import java.text.NumberFormat
import java.util.Locale


@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : AppCompatActivity() {
    /*
    private lateinit var binding: ActivityMainBinding
    var bool: Boolean = true
    var resultat: String = ""
    var nombre1: String = ""
    var nombre2: String = ""
    var op: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Listener_bouton()
        Listener_operation()

        binding.AC.setOnClickListener { boutonAllClear() }
        binding.egal.setOnClickListener { binding.editText.text = boutonEgal() }
        binding.D.setOnClickListener { binding.editText.text = boutonOnClear() }
        binding.op6.setOnClickListener { binding.editText.text = boutonUnaire() }

        // Restaurer l'état s'il existe
        savedInstanceState?.let {
            nombre1 = it.getString("nombre1") ?: "Contenu initial"
            nombre2 = it.getString("nombre2").toString()
            resultat = it.getString("resultat")?:""
            op = it.getString("op").toString()
            bool = it.getBoolean("bool", true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nombre1", nombre1)
        outState.putString("nombre2", nombre2)
        outState.putString("op", op)
        outState.putString("resultat", resultat)
        outState.putBoolean("bool", bool)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        resultat = savedInstanceState.getString("resultat") ?: ""
        nombre1 = savedInstanceState.getString("nombre1") ?: "Contenu initial"
        nombre2 = savedInstanceState.getString("nombre2") ?: ""
        op = savedInstanceState.getString("op") ?: ""
        bool = savedInstanceState.getBoolean("bool", true)
        binding.editText.text = resultat
    }
    */

    private lateinit var binding: ActivityMainBinding
    //variable booleen pour le passage de nombre1 a nombre2
    var bool: Boolean=true
    // declaration de la variable pour l'affichage qui sera initialiser apres
    var resultat:String =""
    //declaration de la variable pour recuperer le premier nombre saisi
    var nombre1: String=""
    //declaration de la variable pour recuperer le deuxieme nombre saisi
    var nombre2: String =""
    //variable operateur
    var op:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Listener_bouton()
        Listener_operation()

        binding.AC.setOnClickListener{ boutonAllClear() }
        binding.egal.setOnClickListener{ binding.editText.text=boutonEgal() }
        binding.D.setOnClickListener{binding.editText.text=boutonOnClear() }
        binding.op6.setOnClickListener{binding.editText.text= boutonUnaire() }


        // Restaurer l'état s'il existe
        if (savedInstanceState != null) {
            nombre1 = savedInstanceState.getString("nombre1") ?: "Contenu initial"
            nombre2 = savedInstanceState.getString("nombre2").toString()
            op = savedInstanceState.getString("op").toString()
            bool = savedInstanceState.getBoolean("bool", true)
            resultat = savedInstanceState.getString("resultat").toString()

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Sauvegarder l'état de vos éléments
        outState.putString("nombre1", nombre1)
        outState.putString("nombre2", nombre2)
        outState.putString("op", op)
        outState.putBoolean("bool", bool)
        outState.putString("resultat", resultat)
    }

    // Restaurer l'état après un changement de configuration
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nombre1 = savedInstanceState.getString("nombre1") ?: "Contenu initial"
        nombre2 = savedInstanceState.getString("nombre2") ?: ""
        op = savedInstanceState.getString("op") ?: ""
        bool = savedInstanceState.getBoolean("bool",true)
        resultat = savedInstanceState.getString("resultat") ?: ""
        binding.editText.text = resultat
    }




    // creation de la fonction qui assigne un ecouteur a chaque bouton numerique
    private fun Listener_bouton() {
        // creation d'un tableau pour recuperer les id des bouton
        val id_boutons = arrayOf(
            R.id.bt0,
            R.id.bt1,
            R.id.bt2,
            R.id.bt3,
            R.id.bt4,
            R.id.bt5,
            R.id.bt6,
            R.id.bt7,
            R.id.bt8,
            R.id.bt9
        )

        //une boucle for pour l'ecoute de chaque id en fonction de sont id
        for (id_bouton in id_boutons) {
            //recuperation de la reference du bouton (permet d'identifer le bouton cliker)
            val button: Button = findViewById(id_bouton)
            //ajout de l'ecouteur sur le bouton
            button.setOnClickListener {
                this.binding.editText.text= execute_bouton(button.text.toString())
            }
        }
    }

    // fonction pour l'execution a chaque click d'un bouton
    @SuppressLint("SetTextI18n")
    private fun execute_bouton(id_bouton: String): String {
        val result:String?
        //si bool = true on remplit nombre1
        if (bool){
            if (nombre1=="0"){
                nombre1=id_bouton
            }else{
                nombre1+=id_bouton
            }
            result=nombre1
        }else{
            nombre2+=id_bouton
            result=nombre1+op+nombre2
        }
        resultat=result
        return result
    }

    //creation de la fonction qui assigne un ecouteur a chaque bouton d'operation
    @SuppressLint("SetTextI18n")
    private fun Listener_operation(){
        // creation d'un tableau pour recuperer les id des operateur
        val id_operateurs= arrayOf(R.id.op1,R.id.op2,R.id.op3,R.id.op4,R.id.op5)

        //une boucle for pour l'ecoute de chaque operateur en fonction de sont id
        for (id_operateur in id_operateurs) {
            //recuperation de la reference de l'operateur (permet d'identifer l'operateur cliker)
            val operateur: Button = findViewById(id_operateur)
            //ecoute de l'operateur
            operateur.setOnClickListener{
                //afficharge a l'ecrant de la valeur retournee par la fonction operation()
                this.binding.editText.text = operation(operateur.text.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun operation(op1:String): String {
        val result: String ?
        if (nombre1==""){
            result="forma non prise en charge"
        }else{
            if (nombre2 !=""){
                val resulta = when (op) {
                    "+" -> nombre1.toInt()+nombre2.toInt()
                    "-" -> nombre1.toInt()-nombre2.toInt()
                    "/" -> nombre1.toInt()/nombre2.toInt()
                    "%" -> nombre1.toInt()%nombre2.toInt()
                    "*" -> nombre1.toInt()*nombre2.toInt()
                    else -> throw IllegalArgumentException("Operateur non prise en charge")
                }
                nombre1=resulta.toString()
                nombre2=""
                result=nombre1+op1
            } else{
                result=nombre1+op1
            }
            bool=false
        }
        op=op1
        resultat=result
        return result
    }

    // fonction pour effacer remetre tous a zero
    private fun boutonAllClear() {
        nombre2=""
        nombre1=""
        bool=true
        op=""
        binding.editText.text=""
        resultat=""
    }

    //fonction pour l'egalite
    private fun boutonEgal() :String{
        if (nombre2 !=""){
            val resulta = when (op) {
                "+" -> nombre1.toInt()+nombre2.toInt()
                "-" -> nombre1.toInt()-nombre2.toInt()
                "/" -> nombre1.toInt()/nombre2.toInt()
                "%" -> nombre1.toInt()%nombre2.toInt()
                "*" -> nombre1.toInt()*nombre2.toInt()
                else -> throw IllegalArgumentException("Operateur non prise en charge")
            }
            nombre1=resulta.toString()
            nombre2=""

        }
        bool=true
        op=""
        resultat=nombre1
        return nombre1
    }

    //fonction pour effacer un nombre
    @SuppressLint("SetTextI18n")
    private fun boutonOnClear() : String{
        val result: String
        if (nombre2 != ""){
            nombre2=nombre2.dropLast(1)
            result=nombre1+op+nombre2
        }else{
            if (op != ""){
                op=op.dropLast(1)
                bool=true
                result=nombre1
            }
            else{
                if (nombre1.length==2 && nombre1.toInt()<0){
                    result=""
                    nombre1=""
                }else{
                    nombre1=nombre1.dropLast(1)
                    result=nombre1
                }
            }
        }
        resultat=result
        return result
    }

    //fonction pour le bouton unaire
    private fun boutonUnaire():String{
        val result:String
        if (nombre2 != ""){
            nombre2=(nombre2.toInt()*(-1)).toString()
            result=nombre1+op+nombre2
        }else{
            if (op != ""){
                nombre2="-"
                result=nombre1+op+nombre2
            }else{
                if (nombre1 !=""){
                    nombre1=(nombre1.toInt()*(-1)).toString()
                    result=nombre1
                }else{
                    nombre1="-"
                    result=nombre1
                }
            }
        }
        resultat=result
        return result
    }

}


/*
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //creation d'une variable boolean pour nous permettre de passer de nombre1 ou nombre2
    private var bool:Boolean = false
    //creation d'une variable boolean pour nous permettre de savoir si nous somme en 1er entrer
    //private var bool1:Boolean = false
    //variable pour l'affichage du resulta
    private lateinit var result:TextView
    //varible pour nombre 1 et 2
    var nombre1="0"
    var nombre2="0"
    //variable operateur
    var op=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result=binding.editText
        //declaration de la variable de la bar vertical
        val verticalBar = binding.verticalBar
        //initialisation et demarage de la fonction de la bar vertical
        blinkVerticalBar(verticalBar)
        //initialisation et demarage de la fonction d'ecoute des boutons
        Listener_bouton()
        //initialisation et demarage de la fonction d'ecoute des operateurs
        Listener_operation()

    }

    // code pour faire clignoter la bar vertical
    private fun blinkVerticalBar(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500 // Durée d'une demi-seconde pour chaque cycle de clignotement
        anim.repeatMode = AlphaAnimation.REVERSE
        anim.repeatCount = AlphaAnimation.INFINITE
        view.startAnimation(anim)
    }

    // creation de la fonction qui assigne un ecouteur a chaque bouton numerique
    private fun Listener_bouton(){
        // creation d'un tableau pour recuperer les id des bouton
        val id_boutons= arrayOf(R.id.bt0,R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6,R.id.bt7,R.id.bt8,R.id.bt9)

        //une boucle for pour l'ecoute de chaque id en fonction de sont id
        for (id_bouton in id_boutons){
            //recuperation de la reference du bouton (permet d'identifer le bouton cliker)
            val button: Button=findViewById(id_bouton)
            //ajout de l'ecouteur sur le bouton
            button.setOnClickListener{
                //affichage du resulta a l'ecrant
                result.append(button.text.toString())
                //si bool = false le nombre1 recoit les valeur entree au clavier
                if(!bool) {
                    if (nombre1 == "0") {
                        nombre1 = button.text.toString()
                    } else {
                        nombre1 = "$nombre1${button.text}"
                    }
                }
                //si bool = true le nombre2 recoit les valeur entre au clavier
                else{
                    if (nombre2 == "0") {
                        //ici nombre2 recoit seulement la valeur du bouton
                        nombre2 = button.text.toString()
                    } else {
                        //ici nombre2 concatenne la valeur de nombre2 et celle du bouton
                        nombre2 = "$nombre2${button.text}"
                    }
                }
            }
        }
    }

    //creation de la fonction qui assigne un ecouteur a chaque bouton d'operation
    @SuppressLint("SetTextI18n")
    private fun Listener_operation(){
        // creation d'un tableau pour recuperer les id des operateur
        val id_operateurs= arrayOf(R.id.op1,R.id.op2,R.id.op3,R.id.op4,R.id.op5,R.id.op6,R.id.op7,R.id.op8,R.id.op9,R.id.op0)

        //une boucle for pour l'ecoute de chaque operateur en fonction de sont id
        for (id_operateur in id_operateurs) {
            //recuperation de la reference de l'operateur (permet d'identifer l'operateur cliker)
            val operateur: Button = findViewById(id_operateur)
            //ecoute de l'operateur
            operateur.setOnClickListener{
                //afficharge a l'ecrant de la valeur retournee par la fonction operation()

                result.text=operation(op)+operateur.text.toString()

                //la variable operateur recupere l'operateur cliker
                op=operateur.text.toString()
                //ecrire ici
            }
        }
    }

    //fonction pour les operation +,-,/,%,x
    @SuppressLint("SetTextI18n")
    private fun operation(op:String):String{
        //creation d'une variable qui prend la valeur a retourner
        var res=""
        // verifie si nombre2 est differant de 0 pour faire des operation
        if (nombre2!="0"){
            val a=when(op){
                "+" -> (nombre1.toInt()+nombre2.toInt())
                "-" -> (nombre1.toInt()-nombre2.toInt())
                "/" -> (nombre1.toInt()/nombre2.toInt())
                "%" -> (nombre1.toInt()%nombre2.toInt())
                "x" -> (nombre1.toInt()*nombre2.toInt())
                "+/-" -> println("x est égal à 3 ou 4")
                "C" -> (
                        result.clearComposingText()
                        )
                "AC" -> println("x est égal à 3 ou 4")

                else -> nombre1.toInt()+nombre2.toInt()
            }
            //bool est mis a false pour permettre l'or du prochain clik sur un nombre de remplire le variable nombre1
            //bool=false
            //la valeur de nombre2 remi a 0 pour eviter de bieser le resulta
            nombre2="0"
            //enfin nombre1 recoit la valeur de l'operation
            nombre1=a.toString()
            //res recoit le resulta convertie en string et concatenee avec l'operateur pour afficher a l'ecrant
            res= a.toString()
        }else{
            //bool est mis a false pour permettre l'or du prochain clik sur un nombre de remplire le variable nombre2
            bool=true
            //res recoit la concatenation de nombre1 avec l'operateur pour afficher a l'ecrant
            res= nombre1
        }
        return res;
    }



}

 */