/** 
 * Copyright (c) Krapht, 2011
 * 
 * "LogisticsPipes" is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package logisticspipes.logistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import logisticspipes.logisticspipes.IRoutedItem;
import logisticspipes.routing.IRouter;
import logisticspipes.utils.ItemIdentifier;
import net.minecraft.item.ItemStack;

public interface ILogisticsManagerV2 {
	
	public IRoutedItem assignDestinationFor(IRoutedItem item, UUID sourceRouterUUID, boolean excludeSource);
	public IRoutedItem destinationUnreachable(IRoutedItem item, UUID currentRouter);
	boolean hasDestination(ItemStack stack, boolean allowDefault, UUID sourceRouter, boolean excludeSource);
	LinkedList<ItemIdentifier> getCraftableItems(List<IRouter> linkedList);
	HashMap<ItemIdentifier, Integer> getAvailableItems(List<IRouter> linkedList);
	String getBetterRouterName(IRouter r);
	//boolean request(LogisticsTransaction transaction, List<IRouter> validDestinations, List<ItemMessage> errors, boolean realrequest, boolean denyCrafterAdding);
	//boolean request(LogisticsTransaction transaction, List<IRouter> validDestinations, List<ItemMessage> errors);
	//boolean request(LogisticsRequest originalRequest, List<IRouter> validDestinations, List<ItemMessage> errors);
}
