(ns courage_wolf_generator.core
  (:gen-class)
  (:use [clojure.contrib.json])
  (:import [java.net URL])
  (:import [java.io BufferedInputStream BufferedOutputStream FileOutputStream])
  (:import [java.net URLEncoder]))

(def username "clojure_rocks")
(def password "get your own password")
(def domain "version1.api.memegenerator.net")
(def generator_id "303")
(def image_id "24")

(defn url-encode [s] (URLEncoder/encode s))

(defn save-url-to-file [url output-file]
  (let  [con    (-> url java.net.URL. .openConnection)
         fields (reduce (fn [h v]
                          (assoc h (.getKey v) (into [] (.getValue v))))
                        {} (.getHeaderFields con))
         size   (first (fields "Content-Length"))
         in     (BufferedInputStream. (.getInputStream con))
         out    (BufferedOutputStream. (FileOutputStream. output-file))
         buffer (make-array Byte/TYPE 1024)]
    (loop [g (.read in buffer)
           r 0]
      (if-not (= g -1)
        (do
          (.write out buffer 0 g)
          (recur (.read in buffer) (+ r g)))))
    (.close in)
    (.close out)
    (.disconnect con)))

(defn download-courage-wolf [header footer output-file]
  (let [uri (reduce (fn [a b] (str a "&" b))
                    (map (fn [[k v]] (str (url-encode k) "=" (url-encode v)))
                         (seq {
                               "username"     username
                               "password"     password
                               "languageCode" "en"
                               "generatorID"  generator_id
                               "text0"        header
                               "text1"        footer
                               "imageID"      image_id
                               })))
        url (str "http://" domain "/Instance_Create?" uri)
        json (slurp url)
        instance-image-uri (((clojure.contrib.json/read-json json) :result) :instanceImageUrl)
        image-url (str "http://" domain instance-image-uri)]
    (do
      (printf "saving %s to %s\n" image-url output-file)
      (save-url-to-file image-url output-file))))

(defn -main [& args]
  (let [info [
              ["PAIN" "IS WEAKNESS LEAVING THE BODY"]
              ["STOP HIDING" "YOU'RE BULLETPROOF"]
              ["DEATH COMES SWIFTLY" "TO ALL WHO OPPOSE YOU"]
              ["PISS LIGHTNING" "SHIT SUCCESS"]
              ["SEIZE THE DAY" "BY THE THROAT"]
              ["WHAT DOESN'T KILL YOU" "DIES HORRIBLY"]
              ["YOUR FRIENDS FEEL INVINCIBLE" "WHEN THEY STAND NEXT TO YOU"]
              ["DON'T TELL THEM TO GO TO HELL" "SEND THEM YOURSELF"]
              ["IF HE KNOCKS YOUR TEETH OUT" "SPIT THEM INTO HIS EYES"]
              ["THEY DIED FOR YOU" "YOU'LL LIVE FOR THEM"]
              ["PAIN IS A MYTH" "YOU ARE A LEGEND"]
              ["LONELY" "ARE THE GLORIUS"]
              ["WHEN THE BODY CRIES \"STOP!\"" "THE SPIRIT SHOUTS \"NEVER!\""]
              ["DON'T LOCK YOUR DOORS" "ACCEPT ALL CHALLENGERS"]
              ["THE MORE YOU SWEAT IN PRACTICE" "THE LESS YOU BLEED IN BATTLE"]
              ["THE POLICE ARE HERE?" "SUCKS TO BE THEM"]
              ["GET HIT BY CAR" "CAR EXPLODES"]
              ["PROCRASTINATE" "LATER"]
              ["AWESOME IS A SYNONYM" "FOR YOU"]
              ["PAIN IS TEMPORARY" "GLORY IS ETERNAL"]
              ["DON'T ASK" "TAKE"]
              ["THEY JUDGE WITH THEIR EYES" "YOU JUDGE WITH YOUR FISTS"]
              ["THE QUESTION ISN'T WHO WILL LET YOU" "IT'S WHO WILL STOP YOU?"]
              ["YOU HAVE" "NO LIMITS"]
              ["YOU ARE" "THE ONE"]
              ["I BELIEVE" "IN YOU"]
              ["YOU'RE BETTER THAN GOD" "HE HAD TO REST"]
              ["BUY FOREIGN" "ENJOY IT"]
              ["SHE'S WAITING" "FOR YOUR CALL"]
              ["SMILE" "SO THEY CAN SEE YOUR TEETH"]
              ["DO MATH" "BECOME UNSTOPPABLE"]
              ["LIFE IS TOUGH" "BE TOUGHER"]
              ["HAVE A DISABILITY" "PLAYING LIFE ON HARD MODE"]
              ["FOOL ME ONCE, THAT'S STRIKE ONE" "FOOL ME TWICE, THAT'S STRIKE THREE"]
              ["THERE IS NO LOSING" "ONLY DELAYED VICTORY"]
              ["LIFE GETTING TOUGH" "MEANS GOD IS AFRAID OF YOUR PROGRESS"]
              ["CAUGHT MASTURBATING" "LOOK THEM IN THE EYE AND FINISH"]
              ["LIFE IS EITHER A DARING ADVENTURE" "OR NOTHING"]
              ["TAKE DRUG TEST" "100%"]
              ["THE MEEK" "INHERIT NOTHING"]
              ["STAB FEAR" "IN THE JUGULAR"]
              ["WHY FEAR THE UNKNOWN" "WHEN YOU CAN CONQUER IT"]
              ["REMEMBER THAT LAST GUY THAT GAVE UP?" "NEITHER DOES ANYONE ELSE"]
              ["TORNADO WARNING IN YOUR AREA" "GRAB CAMERA"]
              ["BETTER TO DIE ON YOUR FEET" "THAN LIVE ON YOUR KNEES"]
              ["IF YOU NEVER GIVE UP" "YOU NEVER LOSE"]
              ["AN OBSTACLE IS ONLY SOMETHING" "YOU HAVEN'T TORN THROUGH YET"]
              ["NO ONE IS TALLER" "THAN THE LAST MAN STANDING"]
              ["IMPOSSIBLE IS A WORD WEAK PEOPLE USE" "TO JUSTIFY GIVING UP"]
              ["DIE LIKE YOU WERE BORN" "SCREAMING AND SOAKED IN BLOOD"]
              ["TAKE CALCULUS TEST" "IN PEN"]
              ["REACH THE FINISH LINE" "KEEP GOING"]
              ["PLAY MONOPOLY" "CONQUER ASIA"]
              ["STAGE 4 CANCER" "BOSS FIGHT"]
              ["HAVE A PARTY" "INVITE THE COPS"]
              ["SMOKE A CIGARETTE" "GIVE IT CANCER"]
              ["HAVE HEART ATTACK" "HEART LOSES"]
              ["COPS PULL YOU OVER" "LET THEM OFF WITH A WARNING"]
              ["WHY FEAR THE UNKNOWN" "WHEN IT CAN FEAR YOU"]
              ["FEAR IS A REFLEX" "COURAGE IS A CHOICE"]
              ["TAKE SLEEPING PILLS" "PULL ALL NIGHTER"]
              ["YOU ARE SOMEBODY'S" "REASON TO MASTURBATE"]
              ["YOU DON'T NEED A NEW JOB" "NEW JOBS NEED YOU"]
              ["COME TO WORK LATE" "ASK FOR RAISE"]
              ["AVOID HANGOVERS" "STAY DRUNK"]
              ["YOU SAY \"BLOODBATH\"" "I SAY \"BATH\""]
              ["HANG THEM WITH THE CHAIN" "THEY ENSLAVE YOU WITH"]
              ["NOT LATER" "NOW"]
              ["LIVE FAST" "GROW OLD"]
              ["READ THE ENTIRE BOOK" "WHILE ON FIRE"]
              ["THERE IS NO \"I\" IN TEAM" "BUT THERE IS ONE IN \"WIN\""]
              ["ONLY DEAD FISH" "GO WITH THE FLOW"]
              ]
        info-length (count info)]
    (doseq [[n [header footer]]
            (map (fn [a b] [a b]) (range info-length) info)]
      (let [output-file (str "/tmp/courage_wolf_" n ".jpg")]
        (download-courage-wolf header footer output-file)))))