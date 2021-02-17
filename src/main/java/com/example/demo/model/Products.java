package com.example.demo.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor

public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Min(0)
    private Double price;

    @Min(0)
    private Long quantity;

    private String image1;
    @Transient
    private MultipartFile productImage1;

    private String image2;
    @Transient
    private MultipartFile productImage2;

    private String image3;
    @Transient
    private MultipartFile productImage3;

    private String image4;
    @Transient
    private MultipartFile productImage4;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name="categorys_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="trademark_id")
    private TradeMark tradeMark;

}
