//Marcas de automoviles
const marcas = Object.freeze({
    CHEVROLET: 'Chevrolet',
    FORD: 'Ford',
    NISSAN: 'Nissan',
    TOYOTA: 'Toyota',
    HONDA: 'Honda',
    VOLKSWAGEN: 'Volkswagen',
    HYUNDAI: 'Hyundai',
    MAZDA: 'Mazda',
    AUDI: 'Audi',
    BMW: 'BMW',
    MERCEDES_BENZ: 'Mercedes-Benz',
});

/*
    Modelos de vehiculos 
*/

//Modelos de automoviles chevrolet
const modelosChevrolet = Object.freeze({
    ONIX: 'Onix',
    AVEO: 'Aveo',
    GROOVE: 'Groove',
    TRACKER: 'Tracker',
    CAMARO: 'Camaro',
    CAVALIER: 'Cavalier',
    CORVETTE: 'Corvette',
    CAPTIVA: 'Captiva',
    TRAX: 'Trax',
    EQUINOX: 'Equinox',
    BLAZER: 'Blazer',
    SUBURBAN: 'Suburban',
    SILVERADO: 'Silverado'
});
//Modelos de automoviles ford
const modelosFord = Object.freeze({
    FIESTA: 'Fiesta',
    FOCUS: 'Focus',
    MUSTANG: 'Mustang',
    EXPLORER: 'Explorer',
    F150: 'F-150',
    RANGER: 'Ranger',
    ESCAPE: 'Escape',
    RAPTOR: 'Raptor'
});
//Modelos de automoviles nissan
const modelosNissan = Object.freeze({
    VERSA: 'Versa',
    SENTRA: 'Sentra',
    ALTIMA: 'Altima',
    XTRAIL: 'X-Trail',
    KICKS: 'Kicks',
    FRONTIER: 'Frontier'
});
//Modelos de automoviles toyota
const modelosToyota = Object.freeze({
    COROLLA: 'Corolla',
    CAMRY: 'Camry',
    RAV4: 'RAV4',
    HIGHLANDER: 'Highlander',
    PRIUS: 'Prius',
    AVANZA: 'Avanza',
    YARIS: 'Yaris',
    HILUX: 'Hilux'
});
//Modelos de automoviles honda
const modelosHonda = Object.freeze({
    CIVIC: 'Civic',
    ACCORD: 'Accord',
    CRV: 'CR-V',
    PILOT: 'Pilot',
    FIT: 'Fit',
    CITY: 'City'
});
//Modelos de automoviles volkswagen
const modelosVolkswagen = Object.freeze({
    GOL: 'Gol',
    VENTO: 'Vento',
    JETTA: 'Jetta',
    TIGUAN: 'Tiguan',
    POLO: 'Polo',
    GOLF: 'Golf'
});
//Modelos de automoviles hyundai
const modelosHyundai = Object.freeze({
    GRANDI10: 'Grand-i10',
    GRANDI20: 'Grand-i20',
    ELANTRA: 'Elantra',
    TUCSON: 'Tucson',
    SANTA_FE: 'Santa Fe'
});
//Modelos de automoviles mazda
const modelosMazda = Object.freeze({
    MAZDA2: 'Mazda2',
    MAZDA3: 'Mazda3',
    CX30: 'CX-30',
    CX3: 'CX-3',    
    CX5: 'CX-5',
    CX9: 'CX-9'
});
//Modelos de automoviles audi
const modelosAudi = Object.freeze({
    A1: 'A1',
    A3: 'A3',
    A4: 'A4',
    A6: 'A6',
    A8: 'A8',
    Q3: 'Q3',
    Q5: 'Q5',
    Q7: 'Q7'
});

/*
    Versione de vehiculos
*/
const versiosChevrolet = Object.freeze({
    LS: 'LS',
    LT: 'LT',
    PREMIER: 'Premier',
    RS: 'RS', 
    ZL1: 'ZL1',
    SS: 'SS',
});
const versionesFord = Object.freeze({
    S: 'S',
    SE: 'SE',
    ST: 'ST',
    TITANIUM: 'Titanium',
    XL: 'XL',
    XLT: 'XLT',
    LARIAT: 'Lariat',
    KING_RANCH: 'King Ranch',
    LIMITED: 'Limited'
});
const versionesNissan = Object.freeze({
    SENCE: 'Sence (TM/TA)',
    ADVANCE: 'Advance (TM/CVT)',
    EXCLUSIVE: 'Exclusive',
    PLATINUM: 'Platinum',
    SR: 'SR',
    XE: 'XE',
    SE: 'SE',
    PRO4X: 'Pro-4X',
    NP300: 'NP-300'
});
const versionesToyota = Object.freeze({
    L: 'L',
    LE: 'LE',
    XLE: 'XLE',
    SE: 'SE',
    XEI: 'XEI',
    LIMITED: 'Limited',
    PLATINUM: 'Platinum',
    SR: 'SR',
    SR5: 'SR5',
});
const vecersionesHonda = Object.freeze({
    LX: 'LX',
    EX: 'EX',
    EXL: 'EX-L',
    SPORT: 'Sport',
    TOURING: 'Touring'
});
const versionesVolkswagen = Object.freeze({
    TRENDLINE: 'Trendline',
    COMFORTLINE: 'Comfortline',
    HIGHLINE: 'Highline',
    RLINE: 'R-Line',
    GTI: 'GTI',
    R: 'R'
});
const versionesHyundai = Object.freeze({
    GL: 'GL',
    GLS: 'GLS',
    GLSPREMIUM: 'GLS-Premium',
    LIMITED: 'Limited',
    PREMIUN: 'Premium'
});
const versionesMazda = Object.freeze({
    I: 'i',
    ISPORT: 'i-Sport',
    IGRAND: 'i-Grand Touring',
    SIGNATURE: 'Signature'
});
const versionesAudi = Object.freeze({
    PREMIUM: 'Premium', 
    PREMIUM_PLUS: 'Premium Plus',
    PRESTIGE: 'Prestige',
    SLINE: 'S line',
    RS: 'RS'
});
const versionesBMW = Object.freeze({
    SERIE_1: 'Serie 1',
    SERIE_2: 'Serie 2', 
    SERIE_3: 'Serie 3',
    SERIE_4: 'Serie 4',
    SERIE_5: 'Serie 5',
    SERIE_6: 'Serie 6',
    SERIE_7: 'Serie 7',
    SERIE_8: 'Serie 8',
    X1: 'X1',   
    X2: 'X2',
    X3: 'X3',
    X4: 'X4',
    X5: 'X5',
    X6: 'X6',
    X7: 'X7',
    M1: 'M1',
    M2: 'M2',
    M3: 'M3',
    M4: 'M4',
    M5: 'M5',
    M6: 'M6',
});
const versionesMercedesBenz = Object.freeze({
    A_CLASS: 'A-Class',
    B_CLASS: 'B-Class',
    C_CLASS: 'C-Class',
    E_CLASS: 'E-Class',
    S_CLASS: 'S-Class',
    GLA: 'GLA',
    GLB: 'GLB',
    GLC: 'GLC',
    GLE: 'GLE',
    GLS: 'GLS',
    G_CLASS: 'G-Class',
    AMG_GT: 'AMG GT'
});
//Metodo para llenar el select de modelos dependiendo de la marca seleccionada
function llenarSelectModelos(marca, selectModelo, selectVersion) {
    switch (marca) {
        case 'CHEVROLET':
            llenar(modelosChevrolet,selectModelo);
            llenarSelectVersiones(versiosChevrolet, selectVersion);
            break;
        case 'FORD':
            llenar(modelosFord,selectModelo);
            llenarSelectVersiones(versionesFord, selectVersion);
            break;
        case 'NISSAN':
            llenar(modelosNissan,selectModelo);
            llenarSelectVersiones(versionesNissan, selectVersion);
            break;
        case 'TOYOTA':
            llenar(modelosToyota,selectModelo);
            llenarSelectVersiones(versionesToyota, selectVersion);
            break;
        case 'HONDA':
            llenar(modelosHonda,selectModelo);
            llenarSelectVersiones(vecersionesHonda, selectVersion);
            break;
        case 'VOLKSWAGEN':
            llenar(modelosVolkswagen,selectModelo);
            llenarSelectVersiones(versionesVolkswagen, selectVersion);
            break;
        case 'HYUNDAI':
            llenar(modelosHyundai,selectModelo);
            llenarSelectVersiones(versionesHyundai, selectVersion);
            break;
        case 'MAZDA':
            llenar(modelosMazda,selectModelo);
            llenarSelectVersiones(versionesMazda, selectVersion);
            break;
        case 'AUDI':
            llenar(modelosAudi,selectModelo);
            llenarSelectVersiones(versionesAudi, selectVersion);
            break;
    }
}

function llenar(modelos, selectModelo) {
   selectModelo.options.length = 1;
    for (const modelo in modelos) {
        const option = document.createElement('option');
        option.value = modelo;
        option.textContent = modelos[modelo];
        selectModelo.appendChild(option);
    }
} 

function llenarSelectVersiones(versiones, selectVersion) {
    selectVersion.options.length = 1;
    for(const version in versiones){
        const option = document.createElement('option');
        option.value = version;
        option.textContent = versiones[version];
        selectVersion.appendChild(option);
    }

}
    