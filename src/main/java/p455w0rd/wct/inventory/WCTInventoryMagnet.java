package p455w0rd.wct.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import p455w0rd.wct.items.ItemMagnet;

public class WCTInventoryMagnet implements IInventory {

	private String name = "MagnetSlot";
	private final ItemStack invItem;
	private ItemStack[] inventory;

	public WCTInventoryMagnet(ItemStack stack) {
		super();
		inventory = new ItemStack[1];
		invItem = stack;
		if (stack != null) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			readFromNBT(stack.getTagCompound());
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasCustomName() {
		return name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				markDirty();
			}
			else {
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void markDirty() {
		writeNBT(invItem.getTagCompound());
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return (itemstack.getItem() instanceof ItemMagnet);
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		NBTTagList tagList = nbtTagCompound.getTagList(name, 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			int slot = tagCompound.getInteger("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
	}

	public void writeNBT(NBTTagCompound nbtTagCompound) {
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
			if (inventory[currentIndex] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setInteger("Slot", currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag(name, tagList);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(name);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}
}
