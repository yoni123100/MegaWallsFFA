package me.main.yoni.API;

import org.bukkit.ChatColor;

public class Timeformatter {
	
	  public static int formatFromLetters(String s) {
		  s = s.toUpperCase();
		  s = s.replace(" ", "");
		  s = ChatColor.stripColor(s);
		  if(s.contains("IX")) {
			  return 9;
		  }
		  if(s.contains("VIII")) {
			  return 8;
		  }
		  if(s.contains("VII")) {
			  return 7;
		  }
		  if(s.contains("VI")) {
			  return 6;
		  }
		  if(s.contains("V")) {
			  return 5;
		  }
		  if(s.contains("IV")) {
			  return 4;
		  }
		  if(s.contains("III")) {
			  return 3;
		  }
		  if(s.contains("II")) {
			  return 2;
		  }
		  if(s.contains("I")) {
			  return 1;
		  }
		  return 0;
	  }
	
	  public static String formatForUpgradeLvls(int input) {
		    if (input < 1 || input > 3999)
		        return "Invalid Roman Number Value";
		    String s = "";
		    while (input >= 1000) {
		        s += "M";
		        input -= 1000;        }
		    while (input >= 900) {
		        s += "CM";
		        input -= 900;
		    }
		    while (input >= 500) {
		        s += "D";
		        input -= 500;
		    }
		    while (input >= 400) {
		        s += "CD";
		        input -= 400;
		    }
		    while (input >= 100) {
		        s += "C";
		        input -= 100;
		    }
		    while (input >= 90) {
		        s += "XC";
		        input -= 90;
		    }
		    while (input >= 50) {
		        s += "L";
		        input -= 50;
		    }
		    while (input >= 40) {
		        s += "XL";
		        input -= 40;
		    }
		    while (input >= 10) {
		        s += "X";
		        input -= 10;
		    }
		    while (input >= 9) {
		        s += "IX";
		        input -= 9;
		    }
		    while (input >= 5) {
		        s += "V";
		        input -= 5;
		    }
		    while (input >= 4) {
		        s += "IV";
		        input -= 4;
		    }
		    while (input >= 1) {
		        s += "I";
		        input -= 1;
		    }    
		    return s;
		}
	  
		public static String formatTime(int i) {
			int seconds = i;
			int minutes =0;
			int hours =  0;
			int days = 0;
			int weeks = 0;
			int months = 0;
			int years = 0;
			while(seconds >= 60) {
				seconds -= 60;
				minutes++;
			}
			while(minutes >= 60) {
				minutes -= 60;
				hours++;
			}
			while(hours >= 24) {
				hours -= 24;
				days++;
			}
			while(days >= 30) {
				days -= 30;
				months++;
			}
			while(days >= 7) {
				days -= 7;
				weeks++;
			}
			while(months >= 12) {
				months -= 12;
				years++;
			}
			StringBuilder sb = new StringBuilder();
			if(years != 0) {
				if(years < 10) {
					sb.append("0");
				}
				sb.append(years+":");
			}
			if(months != 0) {
				if(months < 10) {
					sb.append("0");
				}
				sb.append(months+":");
			}
			if(weeks != 0) {
				if(weeks < 10) {
					sb.append("0");
				}
				sb.append(weeks+":");
			}
			if(days != 0) {
				if(days < 10) {
					sb.append("0");
				}
				sb.append(days+":");
			}
			if(hours < 10) {
				sb.append("0");
			}
			sb.append(hours+":");
			if(minutes < 10) {
				sb.append("0");
			}
			sb.append(minutes+":");
			if(seconds < 10) {
				sb.append("0");
			}
			sb.append(seconds);
			return sb.toString();
		}

}
