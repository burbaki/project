/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package market;

public interface ITrader {

    public void takeProductPack(ProductPack pack);

    public void giveProductPack(ProductPack pack);

    public void takeMoney(double money);

    public void giveMoney(double money);
}