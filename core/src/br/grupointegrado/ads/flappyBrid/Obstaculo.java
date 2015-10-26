package br.grupointegrado.ads.flappyBrid;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Luan on 26/10/2015.
 */
public class Obstaculo {
    private World mundo;
    private OrthographicCamera camera;
    private Body corpoCima, corpoBaixo;
    private float posX;
    private float posYcima, posYbaixo;
    private float largura, altura;
    private boolean passou;

    private Obstaculo ultimoObstaculo;//ultima antes do atual

    public Obstaculo(World mundo, OrthographicCamera camera, Obstaculo ultimoObstaculo){
        this.mundo = mundo;
        this.camera = camera;
        this.ultimoObstaculo = ultimoObstaculo;
        initPosicao();
        initCorpoCima();
        initCorpoBaixo();
    }

    private void initCorpoBaixo() {
        corpoBaixo = Util.criarCorpo(mundo, BodyDef.BodyType.StaticBody, posX, posYbaixo);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(largura / 2, altura / 2);

        Util.criarForma(corpoBaixo, shape, "OBSTACULO_BAIXO");

        shape.dispose();
    }

    private void initCorpoCima() {
        corpoCima = Util.criarCorpo(mundo, BodyDef.BodyType.StaticBody, posX, posYcima);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(largura / 2, altura / 2);

        Util.criarForma(corpoCima, shape, "OBSTACULO_CIMA");

        shape.dispose();
    }

    private void initPosicao() {
        largura = 40 / Util.PIXEL_METRO;
        altura = camera.viewportHeight / Util.PIXEL_METRO;

        float xInicial = largura;
        if(ultimoObstaculo != null)
            xInicial = ultimoObstaculo.getPosX();


        posX = xInicial + 8; //e o espaco entre os obstaculo

        float parcela = (altura - Util.ALTURA_CHAO) / 6; //tamanho da tela divido por 6, para encontrar a posicao do y do obstaculo

        int multiplicar = MathUtils.random(1, 3);//numero aleatorio entre 1 e 3
        posYbaixo = Util.ALTURA_CHAO + (parcela * multiplicar) - (altura / 2);
        posYcima = posYbaixo + altura + 2f;//2f espaco entre  os canos
    }

    private float getPosX() {
        return  this.posX;
    }

    public void  remover(){
        mundo.destroyBody(corpoCima);
        mundo.destroyBody(corpoBaixo);
    }
}
