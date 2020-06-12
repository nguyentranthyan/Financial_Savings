package com.example.financial_savings.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.financial_savings.daos.ChiTietNganSachDAO;
import com.example.financial_savings.daos.DanhMucDAO;
import com.example.financial_savings.daos.NganSachDAO;
import com.example.financial_savings.daos.SinhVienDAO;
import com.example.financial_savings.daos.SoGiaoDichDAO;
import com.example.financial_savings.daos.SuKienDAO;
import com.example.financial_savings.daos.TaiKhoanDAO;
import com.example.financial_savings.daos.TietKiemDAO;
import com.example.financial_savings.daos.ViCaNhanDAO;
import com.example.financial_savings.dbs.ChiTietNganSachDBS;
import com.example.financial_savings.dbs.DanhMucDBS;
import com.example.financial_savings.dbs.NganSachDBS;
import com.example.financial_savings.dbs.SinhVienDBS;
import com.example.financial_savings.dbs.SoGiaoDichDBS;
import com.example.financial_savings.dbs.SuKienDBS;
import com.example.financial_savings.dbs.TaiKhoanDBS;
import com.example.financial_savings.dbs.TietKiemDBS;
import com.example.financial_savings.dbs.ViCaNhanDBS;
import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FinancialSavings_DBS";
    private static final int DATABASE_VERSION = 1;

    private ChiTietNganSachDAO chiTietNganSachDAO = new ChiTietNganSachDAO();
    private DanhMucDAO danhMucDAO = new DanhMucDAO();
    private NganSachDAO nganSachDAO = new NganSachDAO();
    private SinhVienDAO sinhVienDAO = new SinhVienDAO();
    private SoGiaoDichDAO soGiaoDichDAO = new SoGiaoDichDAO();
    private SuKienDAO suKienDAO = new SuKienDAO();
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private TietKiemDAO tietKiemDAO = new TietKiemDAO();
    private ViCaNhanDAO viCaNhanDAO = new ViCaNhanDAO();

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaiKhoanDBS.createTable());
        db.execSQL(SuKienDBS.createTable());
        db.execSQL(DanhMucDBS.createTable());
        db.execSQL(ViCaNhanDBS.createTable());
        db.execSQL(TietKiemDBS.createTable());
        db.execSQL(NganSachDBS.createTable());
        db.execSQL(SinhVienDBS.createTable());
        db.execSQL(SoGiaoDichDBS.createTable());
        db.execSQL(ChiTietNganSachDBS.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ChiTietNganSachDBS.deleteTable());
        db.execSQL(DanhMucDBS.deleteTable());
        db.execSQL(NganSachDBS.deleteTable());
        db.execSQL(SinhVienDBS.deleteTable());
        db.execSQL(SoGiaoDichDBS.deleteTable());
        db.execSQL(SuKienDBS.deleteTable());
        db.execSQL(TaiKhoanDBS.deleteTable());
        db.execSQL(TietKiemDBS.deleteTable());
        db.execSQL(ViCaNhanDBS.deleteTable());
        onCreate(db);
    }

    // -----------------Chi Tiet Ngan Sach----------------- //

    public boolean insert_ChiTietNganSach(ChiTietNganSach chiTietNganSach) {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.insert(db, chiTietNganSach);
    }

    public ChiTietNganSach getByID_ChiTietNganSach(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.getByID(db, id);
    }

    public ArrayList<ChiTietNganSach> getByIDBudget_ChiTietNganSach(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.getByIDBudget(db, id);
    }

    public ArrayList<ChiTietNganSach> getByIDTrans_ChiTietNganSach(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.getByIDTrans(db, id);
    }

    public ArrayList<ChiTietNganSach> getAll_ChiTietNganSach() {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.getAll(db);
    }

    public boolean delete_ChiTietNganSach(ChiTietNganSach chiTietNganSach) {
        SQLiteDatabase db = this.getWritableDatabase();
        return chiTietNganSachDAO.delete(db, chiTietNganSach);
    }

    // -----------------Danh Muc----------------- //

    public boolean insert_DanhMuc(DanhMuc danhMuc) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.insert(db, danhMuc);
    }

    public DanhMuc getByID_DanhMuc(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.getByID(db, id);
    }

    public DanhMuc getByName_DanhMuc(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.getByName(db, name);
    }

    public ArrayList<DanhMuc> getByCate_DanhMuc(String cate) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.getByCategory(db, cate);
    }

    public ArrayList<DanhMuc> getByWallet_DanhMuc(String wallet) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.getByWallet(db, wallet);
    }

    public ArrayList<DanhMuc> getAll_DanhMuc() {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.getAll(db);
    }

    public boolean delete_DanhMuc(DanhMuc danhMuc) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.delete(db, danhMuc);
    }

    public boolean update_DanhMuc(DanhMuc danhMuc) {
        SQLiteDatabase db = this.getWritableDatabase();
        return danhMucDAO.update(db, danhMuc);
    }

    // -----------------Ngan Sach----------------- //

    public boolean insert_NganSach(NganSach nganSach) {
        SQLiteDatabase db = this.getWritableDatabase();
        return nganSachDAO.insert(db, nganSach);
    }

    public NganSach getByID_NganSach(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return nganSachDAO.getByID(db, id);
    }

    public ArrayList<NganSach> getAll_NganSach() {
        SQLiteDatabase db = this.getWritableDatabase();
        return nganSachDAO.getAll(db);
    }

    public boolean delete_NganSach(NganSach nganSach) {
        SQLiteDatabase db = this.getWritableDatabase();
        return nganSachDAO.delete(db, nganSach);
    }

    public boolean update_NganSach(NganSach nganSach) {
        SQLiteDatabase db = this.getWritableDatabase();
        return nganSachDAO.update(db, nganSach);
    }

    // -----------------Sinh Vien----------------- //

    public boolean insert_SinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return sinhVienDAO.insert(db, sinhVien);
    }

    public SinhVien getByID_SinhVien(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return sinhVienDAO.getByID(db, id);
    }

    public ArrayList<SinhVien> getAll_SinhVien() {
        SQLiteDatabase db = this.getWritableDatabase();
        return sinhVienDAO.getAll(db);
    }

    public boolean delete_SinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return sinhVienDAO.delete(db, sinhVien);
    }

    public boolean update_SinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return sinhVienDAO.update(db, sinhVien);
    }

    // -----------------So Giao Dich----------------- //

    public boolean insert_SoGiaoDich(SoGiaoDich soGiaoDich) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.insert(db, soGiaoDich);
    }

    public SoGiaoDich getByID_SoGiaoDich(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getByID(db, id);
    }

    public ArrayList<SoGiaoDich> getByEvent_SoGiaoDich(String idEvent) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getByEvent(db, idEvent);
    }

    public ArrayList<SoGiaoDich> getBySavings_SoGiaoDich(String idSavings) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getBySavings(db, idSavings);
    }

    public ArrayList<SoGiaoDich> getByWallet_SoGiaoDich(String idWallet) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getByWallet(db, idWallet);
    }

    public ArrayList<SoGiaoDich> getByStatus_SoGiaoDich(int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getByStatus(db, status);
    }

    public ArrayList<SoGiaoDich> getAll_SoGiaoDich() {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.getAll(db);
    }

    public boolean delete_SoGiaoDich(SoGiaoDich soGiaoDich) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.delete(db, soGiaoDich);
    }

    public boolean update_SoGiaoDich(SoGiaoDich soGiaoDich) {
        SQLiteDatabase db = this.getWritableDatabase();
        return soGiaoDichDAO.update(db, soGiaoDich);
    }

    // -----------------Su Kien----------------- //

    public boolean insert_SuKien(SuKien suKien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.insert(db, suKien);
    }

    public SuKien getByID_SuKien(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.getByID(db, id);
    }

    public SuKien getByName_SuKien(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.getByName(db, name);
    }

    public ArrayList<SuKien> getAll_SuKien() {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.getAll(db);
    }

    public boolean delete_SuKien(SuKien suKien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.delete(db, suKien);
    }

    public boolean update_SuKien(SuKien suKien) {
        SQLiteDatabase db = this.getWritableDatabase();
        return suKienDAO.update(db, suKien);
    }

    // -----------------Tai Khoan----------------- //

    public boolean insert_TaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.insert(db, taiKhoan);
    }

    public TaiKhoan getByID_TaiKhoan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.getByID(db, id);
    }

    public TaiKhoan getByCode_TaiKhoan(int code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.getByCode(db, code);
    }

    public ArrayList<TaiKhoan> getAll_TaiKhoan() {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.getAll(db);
    }

    public boolean delete_TaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.delete(db, taiKhoan);
    }

    public boolean update_TaiKhoan(TaiKhoan taiKhoan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return taiKhoanDAO.update(db, taiKhoan);
    }

    // -----------------Tiet Kiem----------------- //

    public boolean insert_TietKiem(TietKiem tietKiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.insert(db, tietKiem);
    }

    public TietKiem getByID_TietKiem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.getByID(db, id);
    }

    public TietKiem getByName_TietKiem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.getByName(db, name);
    }

    public ArrayList<TietKiem> getAll_TietKiem() {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.getAll(db);
    }

    public boolean delete_TietKiem(TietKiem tietKiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.delete(db, tietKiem);
    }

    public boolean update_TietKiem(TietKiem tietKiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        return tietKiemDAO.update(db, tietKiem);
    }

    // -----------------Vi Ca Nhan----------------- //

    public boolean insert_ViCaNhan(ViCaNhan viCaNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.insert(db, viCaNhan);
    }

    public ViCaNhan getByID_ViCaNhan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.getByID(db, id);
    }

    public ViCaNhan getByName_ViCaNhan(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.getByName(db, name);
    }

    public ArrayList<ViCaNhan> getAll_ViCaNhan() {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.getAll(db);
    }

    public ArrayList<ViCaNhan> getAllAnother_ViCaNhan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.getAllAnother(db, id);
    }

    public boolean delete_ViCaNhan(ViCaNhan viCaNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.delete(db, viCaNhan);
    }

    public boolean update_ViCaNhan(ViCaNhan viCaNhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        return viCaNhanDAO.update(db, viCaNhan);
    }
}
