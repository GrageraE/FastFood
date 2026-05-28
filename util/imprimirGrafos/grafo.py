import matplotlib.pyplot as plt
import networkx as nx

# 1. Definir la lista de aristas (conexiones entre nodos)
aristas = [("A", "B"), ("B", "C"), ("C", "D"), ("D", "A"), ("A", "C")]

# 2. Crear un grafo vacío
G = nx.Graph()

# 3. Añadir las aristas al grafo
G.add_edges_from(aristas)

# 4. Dibujar el grafo
nx.draw(G, with_labels=True, node_color="skyblue", node_size=700, font_weight="bold")

# 5. Mostrar en pantalla
plt.show()
