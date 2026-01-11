package com.blockdude.game.data

object LevelRepository {

    fun getAllLevels(): List<Level> = listOf(
        // Level 1
        Level.fromString(
            id = 1,
            name = "Level 1",
            levelString = """
                ################












                    #      #   D
                 B P#B     #  ##
                ################
            """
        ),

        // Level 2
        Level.fromString(
            id = 2,
            name = "Level 2",
            levelString = """
                ################










                 P
                ##B         ###
                #### #      ###D
                #### ###B ######
                ################
            """
        ),

        // Level 3
        Level.fromString(
            id = 3,
            name = "Level 3",
            levelString = """
                ################









                B
                ##
                  #        #
                   ##      #
                 P     B  B#   D
                ################
            """
        ),

        // Level 4
        Level.fromString(
            id = 4,
            name = "Level 4",
            levelString = """
                ################





                               D
                               #
                BP            ##
                BB       #   ###
                BBBB     ## ####
                #####   ### ####
                #####  #########
                ##### ##########
                ##### ##########
                ################
            """
        ),

        // Level 5
        Level.fromString(
            id = 5,
            name = "Level 5",
            levelString = """
                ################




                B
                BB  P
                BBB #    #
                #####    ## ###
                #####  #### ###
                ##### #########
                ##### #########
                ##### #########D
                ################
                ################
                ################
            """
        ),

        // Level 6
        Level.fromString(
            id = 6,
            name = "Level 6",
            levelString = """
                ################


                          #####
                         #    #
                        ##    #
                       ##     #
                       ##     #
                               D
                # ##           #
                #######        #
                #####  #     BB#
                            BBB#
                           BBBB#
                 B P #     BBBB#
                ################
            """
        ),

        // Level 7
        Level.fromString(
            id = 7,
            name = "Level 7",
            levelString = """
                ################

                        B  B
                       ########
                       #
                     B #  B  B
                B   #### #######
                BB     #
                ####   #
                      B#
                     BB#
                P  ##### #
                B ###### #
                ######## #  #
                ######## #  #  D
                ################
            """
        )
    )
}
