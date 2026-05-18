class JuegoSrv:
    def get_resultado(self,secreto,jugado):
        acertados = parciales = 0
        jugado_procesados = []
        secreto_procesados = []
        tamanio = len(secreto)
        for i in range(tamanio):
            if secreto[i] == jugado[i]:
                jugado_procesados.append(True)
                secreto_procesados.append(True)
                acertados += 1
            else:
                jugado_procesados.append(False)
                secreto_procesados.append(False)
        
        for i in range(tamanio):
            for j in range(tamanio):
                if (not jugado_procesados[i] and not secreto_procesados[j] and jugado[i] == secreto[j]):
                    jugado_procesados[i] = True
                    secreto_procesados[j] = True
                    parciales += 1
        return acertados,parciales
                    