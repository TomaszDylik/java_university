# Decisions

### 1. Promocje zrealizujemy wzorcem `Strategy`
Powód:
- promocje mają być dynamicznie dodawane i usuwane,
- każda promocja ma własną logikę,
- nowe promocje będzie można dodawać bez ruszania istniejących klas.
