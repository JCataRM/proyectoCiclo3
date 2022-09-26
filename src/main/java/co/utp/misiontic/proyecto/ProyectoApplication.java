package co.utp.misiontic.proyecto;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import co.utp.misiontic.proyecto.model.entity.*;
import co.utp.misiontic.proyecto.repository.*;
import lombok.*;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Component
	@Data
	@AllArgsConstructor
	public static class cargarDatos implements CommandLineRunner{

		private final EntradaRepositorio entradaRepositorio;
		private final PlatoFuerteRepositorio platoFuerteRepositorio;
		private final PostreRepositorio postreRepositorio;
		private final BebidaRepositorio bebidaRepositorio;

		@Override
		public void run(String... args) throws Exception {
			//cargarEntradas();
			//cargarPlatos();
			//cargarPostres();
			//cargarBebidas();
			
		}

		private void cargarEntradas(){
			entradaRepositorio.saveAll(Arrays.asList(
				new OpcionEntrada(1, "Pan de higos", "2 porciones de pan artesanal recubierto con higos, queso mozzarella y queso crema.", "/images/entrada1.jpg", 12_000, 1),
				new OpcionEntrada(2, "Crostini de ajo", "Pan tostado, queso mozzarella,  ajo,  aceite de oliva, orégano, perejil y tomate.", "/images/entrada2.jpg", 10_000, 1),
				new OpcionEntrada(3,"Elotes mágicos", "2 Mazorcas asadas y parrilladas con mantequilla, aderezadas con mayonesa de la casa y queso mozzarella.", "/images/entrada3.jpg", 12_500, 1),
				new OpcionEntrada(4, "Rollos de pizza horneados", "La más deliciosa masa preparada por la casa,  con jamon, queso oregano y salsa de la casa.", "/images/entrada4.jpg", 20_000, 1),
				new OpcionEntrada(5, "Platano dulce", "Platano frito dulce, puede ser acompañado de queso doblecrema gratinado.", "/images/entrada5.jpg", 15_000, 1)
			));
		}

		private void cargarPlatos(){
			platoFuerteRepositorio.saveAll(Arrays.asList(
				new OpcionPlatoFuerte(1, "Salmón a las finas hierbas", "250gr de salmón asado a la parrilla, con cebolla, pimentón y finas hiervas, bañado en la salsa de la casa.", "/images/plato1.jpg", 40_000, 1),
				new OpcionPlatoFuerte(2, "Camarón apanado", "Camarón, harina de trigo, huevo, orégano, albahaca y salsa de soya.", "/images/plato2.jpg", 25_000, 1),
				new OpcionPlatoFuerte(3, "Filete de ternera", "250 gr de filete jugoso de ternera a la parrilla, con una costra de pimienta y reducción en vino blanco. Acompañado con ensalada de esparragos y tomate cherry, en vinagreta, y papa horneada con salsa dip, ligeramente picante.", "/images/plato3.jpg", 38_000, 1),
				new OpcionPlatoFuerte(4, "Costilla bbq", "300 gr de deliciosa costilla de cerdo asada, acompañada de papas a las francesa ensalada de vegetales y porción de arroz adicional.", "/images/plato4.jpg", 35_000, 1),
				new OpcionPlatoFuerte(5, "Pechuga al ajillo", "350g de trucha a  la plancha con ajo perejil aceite de oliva sal y pimienta. Acompañado de vegetales con arverjas y zanahoria", "/images/plato5.jpg", 26_000, 1),
				new OpcionPlatoFuerte(6, "Pizza de la casa", "pizza mediana (6 porciones) con trocitos de tocineta, mezclados con pollo, peperoni y rúgula.", "/images/plato6.jpg", 29_000, 1),
				new OpcionPlatoFuerte(7, "Pasta pesto", "150g de deliciosos spaguettis o fetuccinis en salsa pesto, con trozos de pechuga de pollo asados al ajillo. Acompañados de ensalada de lechuga, tomate cherry, cebolla y aguacate, en vinagreta.", "/images/plato7.jpg", 27_000, 1)
			));
		}

		private void cargarPostres(){
			postreRepositorio.saveAll(Arrays.asList(
				new OpcionPostre(1, "Tiramisú", "Dos capas de bizcocho Savoiardi sumergido en cafe espresso y dos capas de Crema de queso mascarpone y licor amaretto.", "/images/postre1.jpg", 12_000, 1),
				new OpcionPostre(2, "Flan de leche", "Leche, huevo, caramelo, vainilla y mora.", "/images/postre2.jpg", 12_000, 1),
				new OpcionPostre(3, "Torta de milky way", "Porción de torta jugosa hecha con harina de trigo, leche, azúcar, mantequilla y 200gr de milky way, con 1 capa de milky way y 1 capa de chocolate blanco, y cubierta de chocolate semi-amargo y cereza.", "/images/postre3.jpg", 7_500, 1),
				new OpcionPostre(4, "Pie de limón", "Delicioso postre de limón con galletas, mantequilla, gelatina, crema de leche, leche condendada.", "/images/postre4.jpg", 8_000, 1),
				new OpcionPostre(5, "Brownie", "Delicioso browni con una delgada capa de chocolate suave al paladar", "/images/postre5.jpg", 6_500, 1),
				new OpcionPostre(6, "Cupcake arcoíris", "Esponjoso cupcake de banano, relleno de salsa de chocolate y milo, decorado por encima con crema inglesa, baileys y grageas de colores.", "/images/postre6.jpg", 5_000, 1)
			));
		}

		private void cargarBebidas(){
			bebidaRepositorio.saveAll(Arrays.asList(
				new OpcionBebida(1, "Vino tinto", "Copa de vino tinto de la mejor selección de vinos", "/images/bebida1.jpg", 12_000, 1),
				new OpcionBebida(2, "Bebida de limón y cereza", "Azucar, agua, zumo de limón con trozos de cereza.", "/images/bebida2.jpg", 10_000, 1),
				new OpcionBebida(3, "Malteada", "Leche de soja, almendras, deslactosada o entera, con helado de su elección, cubierta de chantilly, toppings y salsa de chocolota, mora o arequipe.", "/images/bebida3.jpg", 11_400, 1),
				new OpcionBebida(4, "Limonada de la casa", "Azucar, agua, limones, hielo, leche condensada y hojas de menta ", "/images/bebida4.jpg", 10_000, 1),
				new OpcionBebida(5, "Soda", "Cocacola fria o al clima para acompañar sus platos", "/images/bebida5.jpg", 5_500, 1)
			));
		}



		
	}
}
