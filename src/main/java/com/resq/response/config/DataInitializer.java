package com.resq.response.config;

import com.resq.response.document.Resource;
import com.resq.response.document.ResponseTeam;
import com.resq.response.model.GeoLocation;
import com.resq.response.model.ResourceCategory;
import com.resq.response.model.ResourceStatus;
import com.resq.response.model.TeamMember;
import com.resq.response.model.TeamStatus;
import com.resq.response.model.TeamType;
import com.resq.response.repository.ResourceRepository;
import com.resq.response.repository.ResponseTeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ResponseTeamRepository teamRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public DataInitializer(ResponseTeamRepository teamRepository, ResourceRepository resourceRepository) {
        this.teamRepository = teamRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void run(String... args) {
        try {
            if (teamRepository.count() == 0) {
                log.info("Seeding initial demo response teams into MongoDB...");

                ResponseTeam team1 = new ResponseTeam(
                        "RESQ-TEAM-001",
                        "Panadura Water Rescue Unit",
                        TeamType.FLOOD_RESPONSE,
                        TeamStatus.AVAILABLE,
                        Arrays.asList(
                                new TeamMember("Capt. Ruwan Silva", "TEAM_LEADER", "+94771234567"),
                                new TeamMember("Nimal Perera", "PARAMEDIC", "+94772345678"),
                                new TeamMember("Dinesh Jayawardena", "DIVER", "+94773456789")
                        ),
                        new GeoLocation(new BigDecimal("6.7132"), new BigDecimal("79.9074")),
                        Arrays.asList("WATER_RESCUE", "FIRST_AID", "SWIFTWATER_NAVIGATION")
                );

                ResponseTeam team2 = new ResponseTeam(
                        "RESQ-TEAM-002",
                        "Kegalle Mountain & Landslide Search",
                        TeamType.LANDSLIDE_SEARCH,
                        TeamStatus.AVAILABLE,
                        Arrays.asList(
                                new TeamMember("Maj. Kasun Bandara", "TEAM_LEADER", "+94774567890"),
                                new TeamMember("Sunil Karunaratne", "CANINE_HANDLER", "+94775678901")
                        ),
                        new GeoLocation(new BigDecimal("7.2513"), new BigDecimal("80.3464")),
                        Arrays.asList("LANDSLIDE_SEARCH", "K9_SEARCH", "HIGH_ANGLE_RESCUE")
                );

                ResponseTeam team3 = new ResponseTeam(
                        "RESQ-TEAM-003",
                        "Colombo Metro HAZMAT & Fire Rescue",
                        TeamType.FIRE_RESCUE,
                        TeamStatus.AVAILABLE,
                        Arrays.asList(
                                new TeamMember("Commander Priyantha De Silva", "TEAM_LEADER", "+94776789012"),
                                new TeamMember("Ashan Wickramasinghe", "HAZMAT_SPECIALIST", "+94777890123")
                        ),
                        new GeoLocation(new BigDecimal("6.9271"), new BigDecimal("79.8612")),
                        Arrays.asList("FIRE_EXTINGUISHING", "HAZMAT_CONTROL", "STRUCTURAL_SEARCH")
                );

                teamRepository.saveAll(Arrays.asList(team1, team2, team3));
            }

            if (resourceRepository.count() == 0) {
                log.info("Seeding initial demo emergency resources into MongoDB...");

                Resource boat = new Resource("BOAT-001", "Inflatable Rescue Zodiac Boat", ResourceCategory.VEHICLE, 4, 3, ResourceStatus.AVAILABLE, "Panadura Base");
                Resource ambulance = new Resource("AMB-001", "Advanced Life Support Ambulance", ResourceCategory.VEHICLE, 6, 5, ResourceStatus.AVAILABLE, "Colombo Central Base");
                Resource generator = new Resource("GEN-001", "Heavy-Duty Diesel Generator 50kVA", ResourceCategory.EQUIPMENT, 8, 8, ResourceStatus.AVAILABLE, "Kegalle Regional Depot");
                Resource waterRations = new Resource("WAT-001", "Emergency Clean Water Packs (1000L)", ResourceCategory.FOOD_WATER, 50, 45, ResourceStatus.AVAILABLE, "Western Province Warehouse");
                Resource drone = new Resource("DRN-001", "Thermal Imaging Search Drone", ResourceCategory.EQUIPMENT, 5, 4, ResourceStatus.AVAILABLE, "Disaster Management Center");

                resourceRepository.saveAll(Arrays.asList(boat, ambulance, generator, waterRations, drone));
            }
        } catch (Exception e) {
            log.warn("Mongo data initialization skipped/deferred: {}", e.getMessage());
        }
    }
}
