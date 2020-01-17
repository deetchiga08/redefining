package com.briller.service;

import org.hibernate.query.criteria.internal.expression.function.AbsFunction;
import org.springframework.stereotype.Service;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GailModel {



    public Map<String,Object> recodeParameters(Map<String, Integer> value)
    {

        Map<String,Object> value1=new HashMap<String,Object>();

            int age=value.get("age");
            int noOfBiopsy=value.get("no_of_biopsy");
            int hyperPlasia=value.get("biopsy_malignancy");
            int menstrualAge=value.get("first_menstrual_period");
            int firstPregnancyAge=value.get("first_pregnancy_age");
            int firstDegreeRelative=value.get("family_breast_cancer_history_status");
            int raceEthnicity=value.get("race_ethnicity");
            int projectAge=age+5;

            int NBCat = 0;
            int AMCat =0;
            int AFCat=0;
            int NRCat=0;
            double RHyp = 0;
            int errorIndicator=0;

        if(raceEthnicity==99)
        {
            raceEthnicity=0;
        }

        if(hyperPlasia==2)
        {
            hyperPlasia=0;
        }

          if(noOfBiopsy==0 && hyperPlasia==1)
            {
                NBCat=0;
            }
            else if(noOfBiopsy==1)
            {
                NBCat=1;
            }
            else if(noOfBiopsy>=2)
            {
                NBCat=2;
            }
            else
            {
                errorIndicator=1;
            }



            if(menstrualAge>14 && menstrualAge<age || menstrualAge==99)
            {
                AMCat=0;
            }
            else if(menstrualAge>=12 && menstrualAge<14)
            {
                AMCat=1;
            }
            else if(menstrualAge<12)
            {
                AMCat=2;
            }


            if(firstPregnancyAge<20 || firstPregnancyAge==99)
            {
                AFCat=0;
            }
            else if(firstPregnancyAge>=20 && firstPregnancyAge<25)
            {
                AFCat=1;
            }
            else if( firstPregnancyAge>=25 && firstPregnancyAge<30)
            {
                AFCat=2;
            }
            else if(firstPregnancyAge>=30 && firstPregnancyAge<99)
            {
                AFCat=3;
            }


            if(firstDegreeRelative==0 || firstDegreeRelative==99)
            {
                NRCat=0;
            }
            else if(firstDegreeRelative==1)
            {

                NRCat=1;

            }
            else if(firstDegreeRelative>=2)
            {
                if(raceEthnicity>=6 && raceEthnicity<=11)
                {
                    NRCat=1;}
                else{
                    NRCat=2;
                }
            }


            if(NBCat>0 && hyperPlasia==0 && errorIndicator!=1)
            {
                RHyp=0.93;
            }
            else if(NBCat>0 && hyperPlasia==1 && errorIndicator!=1)
            {
                RHyp=1.82;
            }
            else if(NBCat>0 && hyperPlasia==99 && errorIndicator!=1)

            {
                RHyp=1;
            }


            if((raceEthnicity == 2 || raceEthnicity == 3)&& NBCat ==2)
                NBCat = 1;

            if(AFCat==2 || AFCat==3 && firstPregnancyAge!=99 && AFCat==2)
            {
                AFCat=1;
            }



            value1.put("ErrorIndicator",errorIndicator);
            value1.put("Age",age);
            value1.put("projected age",projectAge);
            value1.put("NBCat",NBCat);
            value1.put("AMCat",AMCat);
            value1.put("AFCat",AFCat);
            value1.put("NRCat",NRCat);
            value1.put("Rhyp",RHyp);
            value1.put("hyp",noOfBiopsy);
            value1.put("RhypMiss",hyperPlasia);
            value1.put("race",raceEthnicity);

        return value1;
    }

    public double absoluteRisk(Map<String, Integer> value,int Avg_White)
    {

        //System.out.println("--------------------------------------------------------inside absolute risk dataa==================================================================");
        double value1 = 0;
        double[] White_lambda1 = new double[]{0.000010, 0.000076, 0.000266, 0.000661, 0.001265,
                0.001866, 0.002211, 0.002721, 0.003348, 0.003923, 0.004178,
                0.004439, 0.004421, 0.004109};
        double[] White_lambda1Avg = new double[]{0.0000122, 0.0000741, 0.0002297, 0.0005649,
                0.0011645, 0.0019525, 0.0026154, 0.0030279, 0.0036757,
                0.0042029, 0.0047308, 0.0049425, 0.0047976, 0.0040106};
        double[] White_nlambda1 = new double[]{0.0000120469, 0.0000746893, 0.0002437767,
                0.0005878291, 0.0012069622, 0.0019762053, 0.0026200977,
                0.0033401788, 0.0039743676, 0.0044875763, 0.0048945499,
                0.0051610641, 0.0048268456, 0.0040407389};
        double[] Black_lambda1 = new double[]{0.00002696, 0.00011295, 0.00031094, 0.00067639,
                0.00119444, 0.00187394, 0.00241504, 0.00291112, 0.00310127,
                0.0036656, 0.00393132, 0.00408951, 0.00396793, 0.00363712};
        double[] Hspnc_lambda1 = new double[]{0.0000166, 0.0000741, 0.000274, 0.0006099,
                0.0012225, 0.0019027, 0.0023142, 0.0028357, 0.0031144,
                0.0030794, 0.0033344, 0.0035082, 0.0025308, 0.0020414};
        double[] Other_lambda1 = new double[]{0.000010, 0.000076, 0.000266, 0.000661, 0.001265,
                0.001866, 0.002211, 0.002721, 0.003348, 0.003923, 0.004178,
                0.004439, 0.004421, 0.004109};
        double[] FHspnc_lambda1 = new double[]{0.0000102, 0.0000531, 0.0001578, 0.0003602,
                0.0007617, 0.0011599, 0.0014111, 0.0017245, 0.0020619,
                0.0023603, 0.0025575, 0.0028227, 0.0028295, 0.0025868};
        double[] Chnes_lambda1 = new double[]{0.000004059636, 0.000045944465, 0.000188279352,
                0.000492930493, 0.000913603501, 0.001471537353, 0.001421275482,
                0.001970946494, 0.001674745804, 0.001821581075, 0.001834477198,
                0.001919911972, 0.002233371071, 0.002247315779};
        double[] Japns_lambda1 = new double[]{0.000000000001, 0.000099483924, 0.000287041681,
                0.000545285759, 0.001152211095, 0.001859245108, 0.002606291272,
                0.003221751682, 0.004006961859, 0.003521715275, 0.003593038294,
                0.003589303081, 0.003538507159, 0.002051572909};
        double[] Filip_lambda1 = new double[]{0.000007500161, 0.000081073945, 0.000227492565,
                0.000549786433, 0.001129400541, 0.001813873795, 0.002223665639,
                0.002680309266, 0.00289121923, 0.002534421279, 0.002457159409,
                0.00228661692, 0.001814802825, 0.00175087913};
        double[] Hawai_lambda1 = new double[]{0.000045080582, 0.000098570724, 0.00033997086,
                0.000852591429, 0.001668562761, 0.002552703284, 0.003321774046,
                0.005373001776, 0.005237808549, 0.005581732512, 0.005677419355,
                0.006513409962, 0.003889457523, 0.002949061662};
        double[] OtrPI_lambda1 = new double[]{0.000000000001, 0.000071525212, 0.000288799028,
                0.000602250698, 0.000755579402, 0.000766406354, 0.001893124938,
                0.002365580107, 0.00284393307, 0.002920921732, 0.002330395655,
                0.002036291235, 0.001482683983, 0.001012248203};
        double[] OtrAs_lambda1 = new double[]{0.000012355409, 0.000059526456, 0.000184320831,
                0.000454677273, 0.000791265338, 0.001048462801, 0.001372467817,
                0.001495473711, 0.001646746198, 0.001478363563, 0.001216010125,
                0.0010676637, 0.001376104012, 0.000661576644};
        double[] White_lambda2 = new double[]{0.000493, 0.000531, 0.000625, 0.000825,
                0.001307, 0.002181, 0.003655, 0.005852, 0.009439, 0.015028,
                0.023839, 0.038832, 0.066828, 0.144908};
        double[] White_lambda2Avg = new double[]{0.0004412, 0.0005254, 0.0006746, 0.0009092,
                0.0012534, 0.001957, 0.0032984, 0.0054622, 0.0091035,
                0.0141854, 0.0225935, 0.0361146, 0.0613626, 0.1420663};
        double[] White_nlambda2 = new double[]{0.0004000377, 0.0004280396, 0.0005656742,
                0.0008474486, 0.0012752947, 0.0018601059, 0.0028780622,
                0.0046903348, 0.0078835252, 0.0127434461, 0.0208586233,
                0.0335901145, 0.0575791439, 0.1377327125};
        double[] Black_lambda2 = new double[]{0.00074354, 0.00101698, 0.00145937, 0.00215933,
                0.00315077, 0.00448779, 0.00632281, 0.00963037, 0.01471818,
                0.02116304, 0.03266035, 0.04564087, 0.06835185, 0.13271262};
        double[] Hspnc_lambda2 = new double[]{0.0003561, 0.0004038, 0.0005281, 0.0008875,
                0.0013987, 0.0020769, 0.0030912, 0.004696, 0.007605,
                0.0120555, 0.0193805, 0.0288386, 0.0429634, 0.0740349};
        double[] Other_lambda2 = new double[]{0.000493, 0.000531, 0.000625, 0.000825,
                0.001307, 0.002181, 0.003655, 0.005852, 0.009439, 0.015028,
                0.023839, 0.038832, 0.066828, 0.144908};
        double[] FHspnc_lambda2 = new double[]{0.0003129, 0.0002908, 0.0003515, 0.0004943,
                0.0007807, 0.001284, 0.0020325, 0.0034533, 0.0058674,
                0.0096888, 0.0154429, 0.0254675, 0.0448037, 0.1125678};
        double[] Chnes_lambda2 = new double[]{0.000210649076, 0.000192644865, 0.000244435215,
                0.000317895949, 0.000473261994, 0.00080027138, 0.001217480226,
                0.002099836508, 0.003436889186, 0.006097405623, 0.010664526765,
                0.020148678452, 0.03799079659, 0.098333900733};
        double[] Japns_lambda2 = new double[]{0.000173593803, 0.000295805882, 0.000228322534,
                0.000363242389, 0.000590633044, 0.001086079485, 0.001859999966,
                0.003216600974, 0.004719402141, 0.008535331402, 0.012433511681,
                0.020230197885, 0.037725498348, 0.106149118663};
        double[] Filip_lambda2 = new double[]{0.000229120979, 0.000262988494, 0.00031484409,
                0.000394471908, 0.00064762261, 0.001170202327, 0.001809380379,
                0.002614170568, 0.004483330681, 0.007393665092, 0.012233059675,
                0.021127058106, 0.037936954809, 0.085138518334};
        double[] Hawai_lambda2 = new double[]{0.000563507269, 0.000369640217, 0.001019912579,
                0.001234013911, 0.002098344078, 0.002982934175, 0.005402445702,
                0.009591474245, 0.016315472607, 0.020152229069, 0.02735483871,
                0.050446998723, 0.072262026612, 0.145844504021};
        double[] OtrPI_lambda2 = new double[]{0.000465500812, 0.00060046692, 0.000851057138,
                0.001478265376, 0.001931486788, 0.003866623959, 0.004924932309,
                0.008177071806, 0.00863820289, 0.018974658371, 0.029257567105,
                0.038408980974, 0.052869579345, 0.074745721133};
        double[] OtrAs_lambda2 = new double[]{0.000212632332, 0.000242170741, 0.000301552711,
                0.000369053354, 0.000543002943, 0.000893862331, 0.001515172239,
                0.002574669551, 0.004324370426, 0.007419621918, 0.01325176513,
                0.02229142749, 0.041746550635, 0.087485802065};

        double[] White_1_AR = new double[]{0.5788413, 0.5788413};
        double[] Black_1_AR = new double[]{0.7294988, 0.74397137};
        double[] Hspnc_1_AR = new double[]{0.749294788397, 0.778215491668};
        double[] Other_1_AR = new double[]{0.5788413, 0.5788413};
        double[] FHspnc_1_AR = new double[]{0.428864989813, 0.450352338746};
        double[] Asian_1_AR = new double[]{0.47519806426735, 0.50316401683903};

        //System.out.println("white_1_r"+White_1_AR[1]);

        double[][] Avg_lambda1=new double[14][5];
        /*for (int i = 0; i < 14; i++){
            for (int j = 0; j < 5; j++){
                Avg_lambda1[i][j] = 0;
                //System.out.println("avglamba with zero"+Avg_lambda1[i][j]);
            }
        }*/
        //System.out.println("Avg_lambda1\n-------------------------------------\n");
        for(int i=0;i<=4;i++)
        {
            for(int j=0;j<=13;j++)
            {
                Avg_lambda1[j][i]=White_lambda1Avg[j];
                //System.out.print(Avg_lambda1[j][i] + "\t");
            }
            //System.out.println("\n");
        }
        double[][] Avg_lambda2=new double[14][5];

        //System.out.println("Avg_lambda2\n-------------------------------------\n");
        for(int i=0;i<=4;i++)
        {
            for(int j=0;j<=13;j++)
            {
                Avg_lambda2[j][i]=White_lambda2Avg[j];
                //System.out.print(Avg_lambda2[j][i] + "\t");
            }
            //System.out.println("\n");
        }


        List<double[]> Wrk_lambda1_all = new ArrayList<double[]>();
        Wrk_lambda1_all.add(White_lambda1);
        Wrk_lambda1_all.add(Black_lambda1);
        Wrk_lambda1_all.add(Hspnc_lambda1);
        Wrk_lambda1_all.add(FHspnc_lambda1);
        Wrk_lambda1_all.add(Other_lambda1);
        Wrk_lambda1_all.add(Chnes_lambda1);
        Wrk_lambda1_all.add(Japns_lambda1);
        Wrk_lambda1_all.add(Filip_lambda1);
        Wrk_lambda1_all.add(Hawai_lambda1);
        Wrk_lambda1_all.add(OtrPI_lambda1);
        Wrk_lambda1_all.add(OtrAs_lambda1);

        List<double[]> Wrk_lambda2_all = new ArrayList<double[]>();
        Wrk_lambda2_all.add(White_lambda2);
        Wrk_lambda2_all.add(Black_lambda2);
        Wrk_lambda2_all.add(Hspnc_lambda2);
        Wrk_lambda2_all.add(FHspnc_lambda2);
        Wrk_lambda2_all.add(Other_lambda2);
        Wrk_lambda2_all.add(Chnes_lambda2);
        Wrk_lambda2_all.add(Japns_lambda2);
        Wrk_lambda2_all.add(Filip_lambda2);
        Wrk_lambda2_all.add(Hawai_lambda2);
        Wrk_lambda2_all.add(OtrPI_lambda2);
        Wrk_lambda2_all.add(OtrAs_lambda2);

        List<double[]> Wrk_1_AR_all = new ArrayList<double[]>();
        Wrk_1_AR_all.add(White_1_AR);
        Wrk_1_AR_all.add(Black_1_AR);
        Wrk_1_AR_all.add(Hspnc_1_AR);
        Wrk_1_AR_all.add(FHspnc_1_AR);
        Wrk_1_AR_all.add(Other_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        Wrk_1_AR_all.add(Asian_1_AR);
        //System.out.println("before executing relative risk and recode parameters");
        Map<String,Object> RR_Star = relativeRisk(value);
        Map<String,Object> check_cov = recodeParameters(value);

        int errorIndicator=(int) check_cov.get("ErrorIndicator");
        //System.out.println("error indicator in abolutee risk--------------------------------------------------"+errorIndicator);
        int age=(int) check_cov.get("Age");
        //System.out.println("age"+age);
        int projectAge=(int) check_cov.get("projected age");
        //System.out.println("projection age"+projectAge);
        int NBCat = (int) check_cov.get("NBCat");
       // System.out.println("nbcat in absolute "+NBCat);
        int AMCat = (int) check_cov.get("AMCat");
        //System.out.println("amcat in absolute"+AMCat);
        int AFCat= (int) check_cov.get("AFCat");
        //System.out.println("afcat in absolute"+AFCat);
        int NRCat= (int) check_cov.get("NRCat");
        //System.out.println("nrcat in absolute"+NRCat);
        double Rhyp=(double) check_cov.get("Rhyp");
        //System.out.println("Rhyp in absolute"+ Rhyp);
        int noOfBiopsy=(int) check_cov.get("hyp");
        //System.out.println("noofbiopsy"+noOfBiopsy);
        int hyperPlasia=(int) check_cov.get("RhypMiss");
        //System.out.println("hyperplasia in absolute"+hyperPlasia);
        int race= (int) check_cov.get("race");
        //System.out.println("race in absolute"+race);
        double rrstar1=(double)RR_Star.get("RRStar1");
        double rrstar2=(double)RR_Star.get("RRStar2");


        double absRisk = 0;

        if(errorIndicator==0)
        {
            //System.out.println("inside error indicator==0");
            //System.out.println("rrstar1 in error indicator==0"+rrstar1);
            //ystem.out.println("rrstar2 in error indicator==2"+rrstar2);

            double[] lambda1=new double[70];
            double[] lambda2=new double[70];

            int Strt_Intvl = (int) (Math.floor(age) - 20 + 1);
            //System.out.println("strtintvl"+Strt_Intvl);
            int End_Intvl = (int) (Math.ceil(projectAge) - 20 + 0);
           // System.out.println("end interval"+End_Intvl);
            int NumbrIntvl = (int) (Math.ceil(projectAge) - Math.floor(age));
            //System.out.println("numberintvl"+NumbrIntvl);
            double RskWrk = 0;
            double Cum_lambda=0;
            double[][] lambda1Temp =new double[14][5];
            double[][] lambda2Temp = new double[14][5];
            double[] One_AR_RR=new double[70];
            if(Avg_White==0)
            {

               double One_AR1=Wrk_1_AR_all.get(race)[0];
               // System.out.println("getting value from list and array"+Wrk_1_AR_all.get(0)[0]);
               double One_AR2=Wrk_1_AR_all.get(race)[1];

               double One_AR_RR1= One_AR1* rrstar1;
               // System.out.println("onear-arr1"+One_AR_RR1);
               double One_AR_RR2=One_AR2*rrstar2;

                //System.out.println("one ar ar2"+One_AR_RR2);

               for(int i=0;i<=29;i++)
               {
                   One_AR_RR[i]=One_AR_RR1;
                   //System.out.print(One_AR_RR[i]+"\t");
               }
               for(int i=30;i<=69;i++)
               {
                   One_AR_RR[i]=One_AR_RR2;
                   //System.out.print(One_AR_RR[i]+"\t");
               }

                //System.out.println("--------------------------------------------------------------------------------------------------------------------------");
                for(int i=0;i<=4;i++)
                {
                    for(int j=0;j<=13;j++)
                    {
                        lambda1Temp[j][i]=Wrk_lambda1_all.get(race)[j];
                        //System.out.print(lambda1Temp[j][i]+"\t");
                    }
                }
                //System.out.println("\n");
                for(int i=0;i<=4;i++)
                {
                    for(int j=0;j<13;j++)
                    {
                        lambda2Temp[j][i]=Wrk_lambda2_all.get(race)[j];
                       //System.out.print(lambda2Temp[j][i]+"\t");
                    }
                }

                //System.out.println("\n=====================================================================================================================================\n");
               // System.out.println("the lambda1 2d to 1d");
                int k=0;
                for(int i=0;i<=13;i++) {

                    for(int j=0;j<=4;j++)
                    {
                        double tmp = lambda1Temp[i][j];
                        lambda1[k]=tmp;
                        //System.out.print(lambda1[k]+"\t");
                        k++;
                    }
                }
                //System.out.println("\nthe lambda2 2d to 1d\n----------------------------\n");
                k=0;
                for(int i=0;i<=13;i++) {

                    for(int j=0;j<=4;j++)
                    {

                        double tmp = lambda2Temp[i][j];
                        lambda2[k] = tmp;
                        //System.out.print(k + "-" + lambda2[k]+"\t");
                        k++;
                    }
                }
            }

            if(Avg_White==1)
            {
                for(int i=0;i<70;i++){
                    One_AR_RR[i] = 1;
                }
                //System.out.println("inside avg white ==1");
                //System.out.println("--------------------------------------------------------------------------------------------------------------------------");
                for(int i=0;i<=4;i++)
                {
                    for(int j=0;j<=13;j++)
                    {
                        lambda1Temp[j][i]=Wrk_lambda1_all.get(race)[j];
                        //System.out.print(lambda1Temp[j][i]+"\t");

                    }
                }
                //System.out.println("\n");
                for(int i=0;i<=4;i++)
                {
                    for(int j=0;j<=13;j++)
                    {
                        lambda2Temp[j][i]=Wrk_lambda2_all.get(race)[j];
                        //System.out.print(lambda2Temp[j][i]+"\t");
                    }
                }

               // System.out.println("\n");
                if(race==0 || race==4)
                {
                    lambda1Temp=Avg_lambda1;
                    lambda2Temp=Avg_lambda2;

                }
                //System.out.println("\n");
                int k=0;
                for(int i=0;i<=13;i++) {

                    for (int j = 0; j <= 4; j++) {
                        double tmp = lambda1Temp[i][j];
                        lambda1[k] = tmp;
                        //System.out.print(lambda1[k]+"\t");
                        k++;
                    }}
                //System.out.println("\n");
                //System.out.println("inside lambda2 ");
                k=0;
                for (int i = 0; i <= 13; i++) {
                    for (int j = 0; j <= 4; j++) {
                            double tmp = lambda2Temp[i][j];
                            lambda2[k] = tmp;
                            //System.out.print(lambda2[k]+"\t");
                            k++;

                        }
                }

                }
            System.out.println("-------------------------\nPrinting One_AR_RR\n---------------------------------------\n");
            for(int i=0;i<70;i++)
                System.out.print(One_AR_RR[i]+" ");
            System.out.println("\n------------------------------\nPrinting Lambda1\n----------------------------------\n");
            for(int i=0;i<70;i++)
                System.out.print(lambda1[i]+ " ");

            System.out.println("\n------------------------------\nPrinting Lambda2\n----------------------------------\n");
            for(int i=0;i<70;i++)
                System.out.print(lambda2[i]+ " ");

            System.out.println("printing number_intvl"+NumbrIntvl);
            System.out.println("\n--------------------------------\nPrinting Parameters\n--------------------------------\n");
         for(int j=1;j<=NumbrIntvl;j++)
         {
             System.out.println("number intvl"+NumbrIntvl);
             int IntgrlLngth = 0;
             int j_intvl=Strt_Intvl+j-1;
             System.out.println("j intvl value"+j_intvl);
             if (NumbrIntvl > 1 & j > 1 & j < NumbrIntvl) {
                 System.out.println("------------------------------------------------------------inside first if-----------------------------------------------------");
                 IntgrlLngth = 1;
                System.out.println("integer length value: " +IntgrlLngth);
             }

              else if (NumbrIntvl > 1 & j == 1) {
                 System.out.println("------------------------------------------------------------inside second if-----------------------------------------------------");
                 IntgrlLngth = (int) (1 - (age - Math.floor(age)));
                 System.out.println("integer length value"+IntgrlLngth);
             }
              else if (NumbrIntvl > 1 & j == NumbrIntvl) {
                 System.out.println("------------------------------------------------------------inside third if-----------------------------------------------------");
                int z1 = (projectAge > Math.floor(projectAge))?1: 0;
                 int z2 = (projectAge == Math.floor(projectAge))? 1: 0;
                 IntgrlLngth = (int) (projectAge - Math.floor(projectAge)) * z1 + z2;
                 System.out.println("integer length value"+IntgrlLngth);

             }
             else if (NumbrIntvl == 1) {
                 System.out.println("------------------------------------------------------------inside fourth if-----------------------------------------------------");
                 IntgrlLngth = projectAge - age;
                 System.out.println("integer length value"+IntgrlLngth);
             }

             System.out.println("=========================================================================\n lambda1:"+lambda1[j_intvl]);
             double lambdaj =lambda1[j_intvl-1] * One_AR_RR[j_intvl-1] + lambda2[j_intvl-1];
            System.out.println("Printing lambdaj: "+lambdaj + " One_AR_RR: "+ One_AR_RR[j_intvl]);
            double  PI_j = ((One_AR_RR[j_intvl-1] * lambda1[j_intvl-1]/lambdaj) * Math.exp(-Cum_lambda)) * (1 -Math.exp(-lambdaj * IntgrlLngth));
            System.out.println("Printing PI_j: "+ PI_j);

            RskWrk =  (RskWrk + PI_j);
             Cum_lambda =  (Cum_lambda + lambdaj * IntgrlLngth);

             //System.out.println("risk work"+RskWrk);
             System.out.println("j: "+ j);
             System.out.println("j_intvl: "+j_intvl);
             System.out.println("IntgrlLngth: " +IntgrlLngth);
             System.out.println("Lambdaj: "+lambdaj);
             System.out.println("PI-J: "+PI_j);
             System.out.println("Rsk Wrk: " +RskWrk);
             System.out.println("Cum_lambda: "+ Cum_lambda);
         }

              absRisk=100*RskWrk;
            //System.out.println("absrisk"+absRisk);

         }
        else
        {
            return 0;
        }

         value1 = absRisk;
        //System.out.println("Absolute Risk: "+ value1);
         return value1;
    }


    public Map<String,Object> relativeRisk( Map<String,Integer> value)
    {

        //System.out.println("inside relative risk");
        double[] White_Beta =new double[]{0.5292641686, 0.0940103059, 0.2186262218,
            0.9583027845, -0.288042483, -0.1908113865};
        double[] Black_Beta =new double[]{0.1822121131, 0.2672530336, 0, 0.4757242578,
                -0.1119411682, 0};
        double[] Hspnc_Beta =new double[]{0.0970783641, 0, 0.2318368334, 0.166685441,
                0, 0};
        double[] FHspnc_Beta =new double[]{0.4798624017, 0.2593922322, 0.4669246218,
                0.9076679727, 0, 0};
        double[] Other_Beta =new double[]{0.5292641686, 0.0940103059, 0.2186262218,
                0.9583027845, -0.288042483, -0.1908113865};
        double[] Asian_Beta =new double[]{0.55263612260619, 0.07499257592975, 0.27638268294593,
                0.79185633720481, 0, 0};
        List<double[]> Beta = new ArrayList<double[]>();
        Beta.add(White_Beta);
        Beta.add(Black_Beta);
        Beta.add(Hspnc_Beta);
        Beta.add(FHspnc_Beta);
        Beta.add(Other_Beta);
        Beta.add(Asian_Beta);
        Beta.add(Asian_Beta);
        Beta.add(Asian_Beta);
        Beta.add(Asian_Beta);
        Beta.add(Asian_Beta);
        Beta.add(Asian_Beta);

        //System.out.println("inside relative list value "+Beta);
        Map<String,Object> check_cov=recodeParameters(value);

        //System.out.println("recode function in relative"+check_cov);
        int errorIndicator=(int) check_cov.get("ErrorIndicator");
        //System.out.println("error indicator"+errorIndicator);
        int age=(int) check_cov.get("Age");
        //System.out.println("age in check-cov"+age);
        int projectAge=(int) check_cov.get("projected age");
        //System.out.println("projectage in check-cov"+projectAge);
        int NBCat = (int) check_cov.get("NBCat");
        //System.out.println("nbcat"+NBCat);
        int AMCat = (int) check_cov.get("AMCat");
        //System.out.println("Amcat"+AMCat);
        int AFCat= (int) check_cov.get("AFCat");
        //System.out.println("afcat"+AFCat);
        int NRCat= (int) check_cov.get("NRCat");
        //System.out.println("Nrcat"+NRCat);
        double Rhyp=(double) check_cov.get("Rhyp");
        //System.out.println("rhyp"+Rhyp);
        int noOfBiopsy=(int) check_cov.get("hyp");
        //System.out.println("noofbiopsy"+noOfBiopsy);
        int hyperPlasia=(int) check_cov.get("RhypMiss");
        int race= (int) check_cov.get("race");



        double nbCat= (double) NBCat;
        //System.out.println("nbcat after converting into double"+nbCat);
        double amCat=(double) AMCat;
        //System.out.println("amcat after converting into double"+amCat);
        double afCat=(double) AFCat;
        //System.out.println("afcat after converting into double"+afCat);
        double nrCat=(double) NRCat;
        //System.out.println("the nrcat afater converting into double"+nrCat);



        int patternNumber =NBCat*36+AMCat*12+AFCat*3+ NRCat +1;
        //System.out.println("patternnumber"+patternNumber);
        int n;
        n=race;
        //System.out.println("the race is"+n);

        double LP1=1.0;
        double LP2=1.0;
                if(n>=0)
                {
                    double[] whiteBeta =Beta.get(race);
                    LP1=nbCat* whiteBeta[0]+amCat*whiteBeta[1]+afCat*whiteBeta[2]+nrCat*whiteBeta[3]+afCat*nrCat*whiteBeta[5]+Math.log(Rhyp);
                    //System.out.println("LP1cat"+LP1);
                    LP2=LP1+nbCat*whiteBeta[4];
                    //System.out.println("LP2"+LP2);
                }

        double RR_Star1 = Math.exp(LP1);
        double RR_Star2 = Math.exp(LP2);
        //System.out.println("rrstar1"+RR_Star1);
        //System.out.println("rrstar2"+RR_Star2);

        check_cov.put("RRStar1",RR_Star1);
        check_cov.put("RRStar2",RR_Star2);
        check_cov.put("PatternNumber",patternNumber);


        return check_cov;
    }

    public double riskSummary(Map<String,Integer> value )
    {
        System.out.println("the value inside risksummary"+value);

        System.out.println("near recode parameter function");
        Map<String,Object> recode= recodeParameters(value);
        System.out.println("----------------------------------------\nThe recode function data call:\n-------------------------------------------------\n"+recode);
        //System.out.println("the relativerisk");
        Map<String,Object> RR_Star=relativeRisk(value);
        System.out.println("------------------------------------------\nThe relative risk data:\n-------------------------------------------------------\n"+RR_Star);
        double RR_Star1 = (double) RR_Star.get("RRStar1");
        double RR_Star2 = (double) RR_Star.get("RRStar2");
        //System.out.println("the risk summary"+RR_Star1);
        //System.out.println("the relative summary rrstar2"+RR_Star2);
        double AbsRisk=absoluteRisk(value,0);
        System.out.println("--------------------------------------------\nThe absolute risk value:\n----------------------------------------------------\n "+AbsRisk);
        double AbsRiskAvg=absoluteRisk(value,1);
        System.out.println("---------------------------------------------\nThe absolute risk average:\n---------------------------------------------------\n"+AbsRiskAvg);
        //int time_intvl =value.get("projectAge") - value.get("age");
        //System.out.println("time intvl"+time_intvl);
       // int time_intvl1 = Math.round(time_intvl);
       // System.out.println("time intvl 1"+time_intvl1);
       // Map<String,Object> risk_table = null;

        /*int age=value.get("age");
        int noOfBiopsy=value.get("no_of_biopsy");
        int hyperPlasia=value.get("biopsy_malignancy");
        int menstrualAge=value.get("first_menstrual_period");
        int firstPregnancyAge=value.get("first_pregnancy_age");
        int firstDegreeRelative=value.get("family_breast_cancer_history_status");
        int raceEthnicity=value.get("race_ethnicity");
        int projectAge= (int) recode.get("projectAge");*/
        //double absRisk= (double) AbsRisk.get("absoluteRisk");


        /*risk_table.put("age",age);
        risk_table.put("projectAge",projectAge);
        risk_table.put("timeInterval",time_intvl1);
        risk_table.put("noOfBiopsy",noOfBiopsy);
        risk_table.put("hyperPlasia",hyperPlasia);
        risk_table.put("mesturalAge",menstrualAge);
        risk_table.put("firstPregnancyAge",firstPregnancyAge);
        risk_table.put("firstDegreeRelative",firstDegreeRelative);
        risk_table.put("raceEthnicity",raceEthnicity);
        risk_table.put("RR_Star1",firstPregnancyAge);
        risk_table.put("RR_Star2",firstDegreeRelative);
        risk_table.put("AbsoluteRisk",AbsRisk);*/

        return AbsRisk;
    }


}
















