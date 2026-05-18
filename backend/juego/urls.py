from .apis import GetResultadosApi

from django.urls import path

urlpatterns = [
    path('resultados/', GetResultadosApi.as_view(), name="get_resultado")
]