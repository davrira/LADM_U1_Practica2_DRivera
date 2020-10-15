package mx.tecnm.ladm_u1_practica2_drivera

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGuardar.setOnClickListener {

            if (rbMI.isChecked){

                if(guardarMemoriaInterna()){
                    mensaje("Guardado con exito en memoria interna")
                }else{
                    dialogo("Atencion","Error al guardar en memoria interna","Ok")
                }//if-guardarMemoriaInterna

            }else{

                if (guardarMemoriaSD()){
                    mensaje("Guardado con exito en memoria SD")
                }else{
                    dialogo("Atencion","Error al guardar en memoria SD", "Ok")
                }//if-guardarSD

            }//if-rbMI

        }//btnGuardar


        btnAbrir.setOnClickListener {

            if (rbMI.isChecked){

                if(leerMemoriaInterna()){
                    mensaje("Archivo leido desde memoria interna")
                }else{
                    dialogo("Atencion","Error al leer el archivo o encontrarlo","Ok")
                }//if-leerMemoriaInterna

            }else{

                if(leerMemoriaSD()){
                    mensaje("Archivo leido desde memoria SD")
                }else{
                    dialogo("Atencion","Error al leer archivo o encontrarlo","Ok")
                }//if-leerMemoriaSD

            }//if-rbMI

        }//btnAbrir

    }//onCreate


    private fun guardarMemoriaInterna() : Boolean{

        try {

            var nombreArchivo = txtNombreArchivo.text.toString()
            var flujoSalida = OutputStreamWriter(openFileOutput(nombreArchivo, Context.MODE_PRIVATE))
            var datos = EdTeFrase.text.toString()

            flujoSalida.write(datos)
            flujoSalida.flush()
            flujoSalida.close()

        }catch (ioE : IOException){
            return false
        }

        return true

    }//guardarMemoriaInterna


    private fun leerMemoriaInterna () : Boolean {

        try {

            var nombreArchivo = txtNombreArchivo.text.toString()
            var flujoEntrada = BufferedReader(InputStreamReader(openFileInput(nombreArchivo)))
            var datos = flujoEntrada.readLine()
            var todo = StringBuilder()

            while (datos != null){
                todo.append(datos + "\n")
                datos = flujoEntrada.readLine()
            }

            EdTeFrase.setText(todo.toString())
            flujoEntrada.close()


        }catch (ioE: IOException){
            return false
        }//catch
        return true

    }//leerMemoriaInterna


    private fun guardarMemoriaSD () : Boolean{

        try {

            var nombreArchivoSD = txtNombreArchivo.text.toString()
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, nombreArchivoSD)
            var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))
            var datos = EdTeFrase.text.toString()

            flujoSalida.write(datos)
            flujoSalida.flush()
            flujoSalida.close()

        }catch (ioE: IOException){
            return false
        }

        return true
    }//guardarMemoriaSD


    private fun leerMemoriaSD () : Boolean{

        try {

            var nombreArchivo = txtNombreArchivo.text.toString()
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivos = File(rutaSD.absolutePath, nombreArchivo)
            var flujoEntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivos)))

            var datos = flujoEntrada.readLine()
            var todo = StringBuilder()

            while (datos != null){
                todo.append(datos+"\n")
                datos = flujoEntrada.readLine()
            }

            EdTeFrase.setText(todo)
            flujoEntrada.close()

        }catch (ioE: IOException){
            return false
        }

        return true
    }//leerMemoriaSD


    fun dialogo(titulo:String, mensaje:String, btnOk:String){

        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(btnOk){d,i->d.dismiss()}
            .show()

    }//dialogo


    fun mensaje(mensaje:String){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()
    }//mensaje


}//class