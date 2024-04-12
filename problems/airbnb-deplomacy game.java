Airbnb - deplomacy game
https://1o24bbs.com/t/topic/12548



public static void Battle(String[] input) {
		ArrayList<String> res = new ArrayList<>(); // deal with support
		HashMap<String, List<String>> posMap = new HashMap<>(); // destination/position--army
		HashMap<String, String> resMap = new HashMap<>(); // army--state of army
		HashMap<String, Integer> strengthMap = new HashMap<>();// army--strength

		for (String line : input) {
			String[] parse = line.split(" ");
			String army = parse[0];

			// initialize the strength map
			strengthMap.put(army, 1);

			// initialize the position map
			String pos = "";

			// army stay at the same position
			if (parse[2].equals("Hold") || parse[2].equals("Support")) {
				pos = parse[1];
			}

			// army move to a new place
			if (parse[2].equals("Move")) {
				pos = parse[3];
			}

			// store as {(munich, army name,..)}
			if (!posMap.containsKey(pos)) {
				posMap.put(pos, new ArrayList<String>());
			}
			posMap.get(pos).add(army);
		}

		// update strength map through support
		for (String line : input) {
			String[] parse = line.split(" ");
			if (parse[2].equals("Support")) {
				String supportPos = parse[1];
				String supportTo = parse[3];

				// only has 1 army in that position.
				if (posMap.get(supportPos).size() == 1) { 

					strengthMap.put(supportTo, strengthMap.get(supportTo) + 1);
				}
			}
		}

		// attack compute, loop through position and see if any dest has >2 armies.
		for (String pos : posMap.keySet()) {
			List<String> armyList = posMap.get(pos);
			if (armyList.size() == 1) {
				resMap.put(armyList.get(0), pos);
			} else {
				int maxStrength = 0;
				String win = "";
				for (String army : armyList) {
					int curStrength = strengthMap.get(army);
					if (curStrength > maxStrength) {
						if (win.length() > 0) {
							resMap.put(win, "[dead]");
						}
						maxStrength = curStrength;
						win = army;
						resMap.put(army, pos);
					} else if (curStrength == maxStrength) {
						resMap.put(army, "[dead]");
						resMap.put(win, "[dead]");
					} else if (curStrength < maxStrength) {
						resMap.put(army, "[dead]");
					}
				}
			}
		}
		for (String armyItem : resMap.keySet()) {
			System.out.println(armyItem + " " + resMap.get(armyItem));
		}
	}

	public static void test() {
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, String> res = new HashMap<>();
		map.put("A", 2);
		map.put("B", 3);
		map.put("C", 1);
		int maxStrength = 0;
		String win = "";
		for (String item : map.keySet()) {
			int curStrength = map.get(item);
			if (curStrength > maxStrength) {
				if (win.length() > 0) {
					res.put(win, "[dead]");
				}
				win = item;
				maxStrength = curStrength;
				res.put(win, "Win");
			} else if (curStrength == maxStrength) {
				res.put(win, "[dead]");
				res.put(item, "[dead]");
			} else if (curStrength < maxStrength) {
				res.put(item, "[dead]");
			}
		}

		for (String armyItem : map.keySet()) {
			System.out.println(armyItem + " " + res.get(armyItem));
		}
	}

	public static void main(String[] args) {
		String[] input = new String[] { "A Mu Hold", "B Bohemia Move Mu", "C Pru Move Mu", "D war Hold" };
		String[] input2 = new String[] { "A Mu Hold", "B Bo Move Mu", "C Wa Support B" };
		String[] input3 = new String[] { "D XX Hold", "B Bo Move Mu", "C DF Support B", "M Wa Support B",
				"G FC Move Mu", "F dd Support G" };
		Battle(input3);
	}

