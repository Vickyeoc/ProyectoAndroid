from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response

from juego.service.juegoSrv import JuegoSrv

class GetResultadosApi(APIView):
    def post(self, request):
        secreto = request.data.get("secreto")
        jugado = request.data.get("jugado")
        acertados, parciales = JuegoSrv().get_resultado(secreto, jugado)
        data = {
            "acertados" : acertados,
            "parciales" : parciales
        }
        return Response(data, status=status.HTTP_200_OK)