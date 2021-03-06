/** 
 * Copyright (c) Krapht, 2011
 * 
 * "LogisticsPipes" is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package logisticspipes.logic;

import logisticspipes.LogisticsPipes;
import logisticspipes.network.GuiIDs;
import logisticspipes.pipes.basic.RoutedPipe;
import logisticspipes.proxy.MainProxy;
import logisticspipes.proxy.SimpleServiceLocator;
import logisticspipes.routing.IRouter;
import logisticspipes.routing.ServerRouter;
import net.minecraft.entity.player.EntityPlayer;
import buildcraft.transport.pipes.PipeLogic;

public abstract class BaseRoutingLogic extends PipeLogic{
	
	public RoutedPipe getRoutedPipe(){
		return (RoutedPipe) this.container.pipe;
	}
	
	public IRouter getRouter(){
		return getRoutedPipe().getRouter();
	}
	
	public abstract void onWrenchClicked(EntityPlayer entityplayer);
	
	public abstract void destroy();
	
	protected int throttleTime = 20;
	private int throttleTimeLeft = 0;
	
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (--throttleTimeLeft > 0) return;
		throttledUpdateEntity();
		resetThrottle();
	}
	
	public void throttledUpdateEntity(){}
	
	protected void resetThrottle(){
		throttleTimeLeft = throttleTime;
	}
	
	@Override
	public boolean blockActivated(EntityPlayer entityplayer) {
		if (entityplayer.getCurrentEquippedItem() == null) {
			if (!entityplayer.isSneaking()) return false;
			getRouter().displayRoutes();
			if (LogisticsPipes.DEBUG) {
				doDebugStuff(entityplayer);
			}
			return true;
		} else if (entityplayer.getCurrentEquippedItem().getItem() == LogisticsPipes.LogisticsNetworkMonitior) {
			if(MainProxy.isServer(entityplayer.worldObj)) {
				entityplayer.openGui(LogisticsPipes.instance, GuiIDs.GUI_RoutingStats_ID, worldObj, xCoord, yCoord, zCoord);
			}
			return true;
		} else if (SimpleServiceLocator.buildCraftProxy.isWrenchEquipped(entityplayer)) {
			onWrenchClicked(entityplayer);
			return true;
		} else if (entityplayer.getCurrentEquippedItem().getItem() == LogisticsPipes.LogisticsRemoteOrderer) {
			if(MainProxy.isServer(entityplayer.worldObj)) {
				entityplayer.openGui(LogisticsPipes.instance, GuiIDs.GUI_Normal_Orderer_ID, worldObj, xCoord, yCoord, zCoord);
			}
			return true;
		}
		return super.blockActivated(entityplayer);
	}
	
	private void doDebugStuff(EntityPlayer entityplayer){
		//entityplayer.worldObj.setWorldTime(4951);
		System.out.println("***");
		IRouter r = getRouter();
		if(!(r instanceof ServerRouter)) return;
		ServerRouter sr = (ServerRouter) r;
		
		System.out.println("ID: " + r.getId().toString());
		System.out.println("---------CONNECTED TO---------------");
		for (RoutedPipe adj : sr._adjacent.keySet()) {
			System.out.println(adj.getRouter().getId());
		}
		System.out.println("*******ROUTE TABLE**************");
		for (IRouter p : r.getRouteTable().keySet()) {
			System.out.println(p.getId() + " -> " + r.getRouteTable().get(p).toString());
		}
		
		System.out.println();
		System.out.println();
	}

}
