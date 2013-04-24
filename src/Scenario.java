
public class Scenario {
/*
	Used to set which modules will be run when sent through
	the Simulator.
 */
	private boolean qualityMod;
	private boolean inventoryMod;
	private boolean productionMod;

	private double cashAssets;
	private double salesPrice;
	private String password;
	private double doubleShift;
	private double normalShift;
	private int newEmpAdv;
	private double newHireCost;
	private double severanceCost;
	private int demand;
	private int batchSizeProdSmall;
	private int batchSizeProdLarge;
	private int inventory;
	private double storageCost;
	private double shrinkage;
	private int totalLoss;
	private int loss;
	private int goodsProduced;
	private int defectiveGoodsProduced;
	private double defectiveProdRate;
	private double waterBottles;
	private double labels;
	private double packingCases;
	private double finProdInspection;
	private double finBottleInspection;

	private int changedEmployees;
	private int changedDouble;

	public Scenario(double cashAssets, double salesPrice, String password, int production, double doubleShiftCost, double doubleShiftProdAdv, double normalShiftCost, int newEmpAdv, double newHireCost, double severanceCost, int batchSizeProdSmall, int batchSizeProdLarge, int inventory, double storageCost, double shrinkage, double defectiveProdRate, double waterBottles, double labels, double packingCases, double finProdInspection, double finBottleInspection, int employees) {

		this.cashAssets = cashAssets;
		this.salesPrice = salesPrice;
		this.password = password;
		this.normalShift = normalShift;
		this.doubleShift = doubleShift;
		this.newEmpAdv = newEmpAdv;
		this.newHireCost = newHireCost;
		this.severanceCost = severanceCost;
		this.batchSizeProdSmall = batchSizeProdSmall;
		this.batchSizeProdLarge	= batchSizeProdLarge;
		this.inventory = inventory;
		this.storageCost = storageCost;
		this.shrinkage = shrinkage;
		this.defectiveProdRate = defectiveProdRate;
		this.waterBottles = waterBottles;
		this.labels = labels;
		this.packingCases = packingCases;
		this.finProdInspection = finProdInspection;
		this.finBottleInspection = finBottleInspection;



		this.totalLoss = 0;

	}

	// SETTERS
	public void setQualityMod() {
		qualityMod = true;
	}
	public void setInventoryMod() {
		inventoryMod = true;
	}
	public void setProductionMod() {
		productionMod = true;
	}
	public void setLoss(int loss) {
		this.loss = loss;
		this.totalLoss += loss;
	}
	public void setGoodsProduced(int goodsProduced) {
		this.goodsProduced = goodsProduced;
	}
	public void setDefectiveGoodsProduced(int defectiveGoodsProduced) {
		this.defectiveGoodsProduced = defectiveGoodsProduced;
	}

	// GETTERS
	public boolean isQualityMod() {
		return qualityMod;
	}
	public boolean isInventoryMod() {
		return inventoryMod;
	}
	public boolean isProductionMod() {
		return productionMod;
	}
	public double getCashAssets() {
		return cashAssets;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public String getPassword() {
		return password;
	}
	public double getDoubleShift() {
		return doubleShift;
	}
	public double getNormalShift() {
		return normalShift;
	}
	public int getNewEmpAdv() {
		return newEmpAdv;
	}
	public double getNewHireCost() {
		return newHireCost;
	}
	public double getSeveranceCost() {
		return severanceCost;
	}

	public int getBatchSizeProdSmall() {
		return batchSizeProdSmall;
	}
	public int getBatchSizeProdLarge() {
		return batchSizeProdLarge;
	}
	public int getInventory() {
		return inventory;
	}
	public double getStorageCost() {
		return storageCost;
	}
	public double getShrinkage() {
		return shrinkage;
	}
	public int getTotalLoss() {
		return totalLoss;
	}
	public int getLoss() {
		return loss;
	}
	public int getGoodsProduced() {
		return goodsProduced;
	}
	public int getDefectiveGoodsProduced() {
		return defectiveGoodsProduced;
	}
	public double getDefectiveProdRate() {
		return defectiveProdRate;
	}
	public double getWaterBottles() {
		return waterBottles;
	}
	public double getLabels() {
		return labels;
	}
	public double getPackingCases() {
		return packingCases;
	}
	public double getFinProdInspection() {
		return finProdInspection;
	}
	public double getFinBottleInspection() {
		return finBottleInspection;
	}
	public double getDemandPenalty() {
		return .01;
	}
	public double getDemand() {
		return .01;
	}
}