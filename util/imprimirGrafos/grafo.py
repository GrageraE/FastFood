import matplotlib.pyplot as plt
import networkx as nx



# 2. Crear un grafo vacío
G = nx.DiGraph()

G.add_edge("A", "B")
G.add_edge("B", "C")
G.add_edge("B", "D")

G.add_edge("D", "E")
G.add_edge("D", "F")

G.add_edge("F", "G")
G.add_edge("F", "H")
G.add_edge("H", "I")

pos = {
    "A": (0, 6),
    "B": (0, 5),
    "C": (-2, 4),
    "D": (2, 4),
    "E": (1, 3),
    "F": (3, 3),
    "G": (2.5, 2),
    "H": (3.5, 2),
    "I": (3, 1),
}



#Leyenda para el grafo, para saber que representa cada letra
legend_text = (
    "A - Inicio\n"
    "B - if (LocalDate.parse(fecha).equals(LocalDate.now()))\n"
    "C - False -> Throws InvalidDateException\n"
    "D - True -> Email.parse(auth.getName()) válido\n"
    "E - False -> Throws NotValidEmailException\n"
    "F - True -> if (this.buscarPlatoPorID(idPlato) != null)\n"
    "G - False -> Throws NoExistDBException\n"
    "H - True -> if (plato.getRestaurante().getEmail().equals(emailRest))\n"
    "I - False -> Throws RoleNotAllowedException\n"
    "J - True -> plato.setRebaja(rebaja); rebajasRepository.save(rebaja); "
    "platosRepository.save(plato);\n"
    "I - Fin. Se guarda Rebaja y el plato con nuevo precio en las base de datos correspondientes"
)
plt.figure(figsize=(10, 10))
plt.text(
    -0.15, 0.1,
    legend_text,
    transform=plt.gca().transAxes,
    va='top',
    bbox=dict(facecolor='white', edgecolor='black')
)

#Dibujar el grafo
nx.draw(G, pos, with_labels=True, arrows=True, node_size=2000)
plt.title("Método:PlatosService\npublic void establecerRebaja(String idPlato, double nuevoPrecio, String fechaFin, Authentication auth)")
plt.savefig("grafo.png")
plt.show()
