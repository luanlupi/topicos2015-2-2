package br.grupointegrado.ads.flappyBrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Luan on 05/10/2015.
 */
public class Passaro {
    private final World mundo;
    private final OrthographicCamera camera;
    private final Texture[] texturas;
    private Body corpo;

    public Passaro(World mundo, OrthographicCamera camera, Texture[] texturas){

        this.mundo = mundo;
        this.camera = camera;
        this.texturas = texturas;
        initCorpo();

    }

    private void initCorpo() {
        float x = (camera.viewportWidth / 2) / Util.PIXEL_METRO;
        float y = (camera.viewportHeight / 2) / Util.PIXEL_METRO;

        corpo = Util.criarCorpo(mundo, BodyDef.BodyType.DynamicBody, x, y);

        FixtureDef definicao = new FixtureDef();
        definicao.density = 1; //densidade do corpo
        definicao.friction = 0.04f; //friccao/atrito entre um corpo e outro
        definicao.restitution = 0.3f;//elasticidade do corpo

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("physics/bird.json"));
        loader.attachFixture(corpo, "bird", definicao, 1, "PASSARO");
    }

    /**
     * atualizar o comportamento do passaro
     * @param delta
     */
    public void atualizar(float delta, boolean movimentar){
        if (movimentar)
        atualizarVelocidade();

        atualizarRotacao();

    }

    private void atualizarRotacao() {
        float velocidadeY = corpo.getLinearVelocity().y;
        float rotacao = 0;
        if (velocidadeY < 0){
            //caindo
            rotacao = -15;
        } else if (velocidadeY > 0){
            //caindo
            rotacao = 10;
        }else{
            //reto
            rotacao = 0;
        }
        rotacao = (float) Math.toRadians(rotacao); //convertinfo graus para radianos
        corpo.setTransform(corpo.getPosition(), rotacao);
    }

    private void atualizarVelocidade() {
        corpo.setLinearVelocity(2f, corpo.getLinearVelocity().y);

    }

    /**
     * aplica um forca positiva no y ara simular o pulo
     */
    public void pular(){
        corpo.setLinearVelocity(corpo.getLinearVelocity().x, 0);
        corpo.applyForceToCenter(0, 115, false);
    }

    public Body getCorpo(){
        return corpo;
    }
}
