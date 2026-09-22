/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author AntônioVieira
 */
public class Jogo2 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo2.class.getName());

    /**
     * Creates new form Jogo2
     */
    
     // JButton precisa da importação da sua biblioteca
    //CONTRUTOR DA CLASSE/TELA - SEM ELE A TELA NÃO FUNCIONA
    // matriz com - 10 linhas e 10 colunas
    
    JButton [][] btnCampos = new JButton[10][10];
    
   //MATRIZ PARA GUARDAR AS BOMBAS - TRUE P/ BOMBA, FALSE P/ NUMEROS 
    boolean [][] bombas = new boolean [10][10];
    
    // MATRIZ PARA GUARDAR OS CAMOS QUE FOREM ABERTAS
    boolean [][] abertos = new boolean[10][10];
    
    int quantidadeBombas = 15;
    int quantidadeCasasAbertas=0;
    boolean jogoEncerrado= false;
    
        int segundosPassados = 0;
    Timer cronometro;

    public Jogo2() {
        initComponents();
        Dimension preferredSize;
        //definir tamanho para o painel
        painelCampo.setPreferredSize(new Dimension(800,600));
        
        CriarTabuleiro();
        
        
    }

    //CRIAR AS NOSSA FUNÇÕES/METODOS
  public void CriarTabuleiro(){
  //definir que o painel será divido em 10 linhas e colunas
  // com altura 2px e largura 2px
  painelCampo.setLayout( new GridLayout(10,10,2,2));// GridLayout divide     
  
  for(int coluna=0;coluna<=9;coluna++){
  for(int linha=0;linha<=9;linha++){
  // váriavel botão para guardar os provisorios
  JButton  botao= new JButton();
   botao.setFont(new Font("Arial",Font.BOLD,16));// fonte
   botao.setBackground(new Color(175,175,175));// cor de fundo
   botao.setForeground(Color.WHITE);// cor de texto
  
   // remover marcas do botão quem por padrão
   botao.setFocusPainted(false);
   botao.setEnabled(false);
   final int linhaSelecionada= linha;
   final int colunaSelecionada= coluna;
 //adicionar o evento de clique para abrir as casas  
   botao.addActionListener((ActionEvent Evento) ->{
       abrirBotao(linhaSelecionada,colunaSelecionada);       
       
   });
   //adicionar o batão dentro da matriz
   btnCampos[linha][coluna]=botao;
  //adicionar ele dentro do painel
  painelCampo.add(botao);
   
   
  }// fim do segundo for
  }// fim do primeiro for
  }//fim do metodo CriarTabuleiro    
    
  public void AdicionarBombas(){   
  //Criar uma variavel Rom para gerar valores aleatorios
   Random sorteado = new Random();
  int sorteadoAdicionadas = 0;
   
  while(sorteadoAdicionadas< quantidadeBombas){
      // sortear o n. da lina e coluna que vai ficar a bomba
  int linha = sorteado.nextInt(10);
  int coluna = sorteado.nextInt(10);
  // verifica se não existe bomba adicionada no local
  if(!bombas[linha][coluna]){
  //adicionar a bomba na matriz
  bombas[linha][coluna]=true;
  sorteadoAdicionadas++;
  }
  
  
  
  }
}// fim do AdicinarBombas
  
public void IniciarJogo(){
        LimparJogo();
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarCronometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
            for(int linhas=0;linhas<=9;linhas++){
                JButton botao = btnCampos[linhas][colunas];
                //deixar os botoes visiveis e clicaveis
                botao.setEnabled(true);
            }//fim do 2° for
        }//fim do 1° for
        btnIniciar.setText("REINICIAR");
    }//fim do iniciar jogo
    
    
    public void abrirBotao(int linha, int coluna){
        // verificar se o jogo foi finalizado
        if(jogoEncerrado) return;
        
        //verificar se o botao ja foi aberto
        if(abertos[linha][coluna]) return;
      
        /*se o jogo ainda estiver rodando e o botão ainda não tiver
        sido aberto - então vamos abrir o botão*/
        abertos[linha][coluna]=true;
        quantidadeCasasAbertas++;
        
        // acessar o que tem dentro do botão
        JButton botao = btnCampos[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
           ImageIcon imgBomba = new ImageIcon( 
                   getClass().getResource("/assets/bomba.png"));
           //colocar a imagem no botao
           botao.setIcon(imgBomba);
           FinalizarJogo(false);
           return;
        }else{
            ImageIcon imgBandeira = new ImageIcon(
                getClass().getResource("/assets/bandeira.png"));
            botao.setIcon(imgBandeira);
            return;
        }
        
    }// fim do metodo abrirBotao
    
    
    // este metodo informa quando a pessoa perder ou ganhar o jogo
    public void FinalizarJogo(boolean venceu){
       mostrarBombas();
        //vamos informar que o jogo acabou
        jogoEncerrado=true;
        cronometro.stop();
        
        //verificar se a pessa venceu ou não
        if(venceu){
            JOptionPane.showMessageDialog(
                    this,"Parabéns você venceu!");
            LimparJogo();
        }else{
            JOptionPane.showMessageDialog(
                    this,"Ops, você perdeu o jogo!");
            LimparJogo();
        }
       }//fim do FinalizarJogo
    
    
    public void VerificarVitoria(){
        // armazenar a quantidade de casas com bandeiras
        int casasSemBomba= 100 - quantidadeBombas;
        //se a pessoa abriu todas as bandeiras e não abriu nenhuma bomba
        // então ela venceu o jogo, e o finalizarJogo imprime a mensagem
        if(quantidadeCasasAbertas == casasSemBomba){
            FinalizarJogo(true);
        }
  
        
    }
    
    
    public void IniciarCronometro(){
        // zerar o cronometro caso tenha tido um jogo anterior
        if(cronometro !=null){
            cronometro.stop();
        }
        // reseta o cronometro
        segundosPassados = 0;
        tfTempo.setText("00:00");
        
        // converter o tempo em minutos e segundos
        // o cronometro conta de 1 em 1 segundo, e vai convertendo
        cronometro = new Timer(1000, Evento->{
            segundosPassados++;
            int minutos = segundosPassados/60;
            int horas = minutos/60;
            int segundo = segundosPassados%60;
            //mostrar o tempo dentro da váriavel
            tfTempo.setText(
            String.format("%02d:%02d:%02d",horas,minutos,segundo));
                       
        });
        cronometro.start();
        
        
    }
    
    
    public void LimparJogo(){
         quantidadeCasasAbertas=0;
         jogoEncerrado=false;
        
         
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                bombas[linha][coluna]=false;
                abertos[linha][coluna]=false;
               
                
                //limpeza dos botões
                JButton botao = btnCampos[linha][coluna];
                botao.setIcon(null);
                
            }//fim do 2° for
        }//fim do 1° for
         AdicionarBombas(); 
         IniciarCronometro();
        
    }//fim do LimparJogo
    
    
    public void mostrarBombas(){
       for(int coluna=0;coluna<=9;coluna++){
           for(int linha=0;linha<=9;linha++){
                JButton botao = btnCampos[linha][coluna];
                //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
                if(bombas[linha][coluna]){
                    //variavel que recebe nossa imagem
                   ImageIcon imgBomba = new ImageIcon( 
                           getClass().getResource("/assets/bomba.png"));
                   //colocar a imagem no botao
                   botao.setIcon(imgBomba);
                   
                }//fim do if
           }//fim do 2° for
       }// fim do 1° for      
    }// fim do mostrarBombas

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        tfTempo = new javax.swing.JTextField();
        painelCampo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        titulo.setBackground(new java.awt.Color(204, 204, 204));
        titulo.setFont(new java.awt.Font("Segoe UI Black", 1, 32)); // NOI18N
        titulo.setForeground(new java.awt.Color(0, 51, 51));
        titulo.setText(" Campo Minado");

        btnIniciar.setBackground(new java.awt.Color(0, 51, 51));
        btnIniciar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(0, 153, 0));
        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);

        tfTempo.setEditable(false);
        tfTempo.setBackground(new java.awt.Color(0, 51, 51));
        tfTempo.setForeground(new java.awt.Color(255, 0, 0));
        tfTempo.setText("00:00");

        painelCampo.setBackground(new java.awt.Color(0, 0, 0));
        painelCampo.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout painelCampoLayout = new javax.swing.GroupLayout(painelCampo);
        painelCampo.setLayout(painelCampoLayout);
        painelCampoLayout.setHorizontalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        painelCampoLayout.setVerticalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titulo)
                        .addGap(96, 96, 96)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titulo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(tfTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        IniciarJogo();
    }//GEN-LAST:event_btnIniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JPanel painelCampo;
    private javax.swing.JTextField tfTempo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
