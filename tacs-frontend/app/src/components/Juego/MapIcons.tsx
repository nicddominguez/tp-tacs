import { Icon, BaseIconOptions } from "leaflet";
import ImagenEscudo from "../../assets/img/escudo.svg";
import ImagenEspada from "../../assets/img/espada.svg";
import { MunicipioEnJuegoModel, UsuarioModel } from "api";

function buildIcon(img: string, size: [number, number]) {
  const options: BaseIconOptions = {
    iconUrl: img,
    iconSize: size,
  };
  return new Icon(options);
}

function iconoEspada() {
  return buildIcon(ImagenEspada, [40, 40]);
}

function iconoEscudo() {
  return buildIcon(ImagenEscudo, [40, 40]);
}

export default function iconoParaMunicipio(
  municipio: MunicipioEnJuegoModel,
  jugadorLogueado?: UsuarioModel
) {
  if (!municipio.duenio || !jugadorLogueado) {
    return undefined;
  }

  if (municipio.duenio.id === jugadorLogueado.id) {
    return iconoEscudo();
  }

  return iconoEspada();
}
