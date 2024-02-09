package com.example.test
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException
import java.text.NumberFormat
import java.util.Locale


@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : AppCompatActivity() {

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
    //variable pour le presse papier
    private lateinit var pressPapier:TextView
    @SuppressLint("SetTextI18n")
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
        pressPapier=binding.editText

        // Enregistrez le TextView pour le menu contextuel
        registerForContextMenu(pressPapier)

        // Ajoutez un texte d'exemple pour tester
            //pressPapier.text = "Sélectionnez ce texte pour copier ou coller."

    }

    // Définissez le menu contextuel pour le TextView
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Options")
        menu.add(0, v.id, 0, "Copier")
        menu.add(0, v.id, 0, "Coller")
    }
    // Gérez les actions du menu contextuel
    override fun onContextItemSelected(item: MenuItem): Boolean {
      //  val selectionStart = pressPapier.selectionStart
      //  val selectionEnd = pressPapier.selectionEnd
        val selectedText =binding.editText.text.toString()
        when (item.title) {
            "Copier" -> copyToClipboard(selectedText.toString())
            "Coller" -> pasteFromClipboard()
        }
        return true
    }

    // Fonction pour copier du texte dans le presse-papiers
    private fun copyToClipboard(text: String) {
        if (text.isNotEmpty()) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Label", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Texte copié dans le presse-papiers", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Le texte est vide, impossible de copier", Toast.LENGTH_SHORT).show()
        }
    }



    // Fonction pour coller du texte depuis le presse-papiers
    private fun pasteFromClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip()) {
            val pasteData = clipboard.primaryClip?.getItemAt(0)?.text.toString()
            // Faites quelque chose avec le texte collé, par exemple, mettez-le dans le TextView
            this.binding.editText.text = pasteData
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
                val resulta = calcul(op)
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
            val resulta = calcul(op)
            nombre1=resulta.toString()
            nombre2=""

        }
        bool=true
        op=""
        resultat=nombre1
        return nombre1
    }

    //FONCTION POUR CALCULER
    fun calcul(operateur: String): Int{

         val res = when (operateur) {
            "+" -> nombre1.toInt()+nombre2.toInt()
            "-" -> nombre1.toInt()-nombre2.toInt()
            "/" -> nombre1.toInt()/nombre2.toInt()
            "%" -> nombre1.toInt()%nombre2.toInt()
            "*" -> nombre1.toInt()*nombre2.toInt()
            else -> throw IllegalArgumentException("Operateur non prise en charge")
        }
        return res
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
